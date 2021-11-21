package com.example.demo.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.demo.model.Item;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ItemsService {
	
private static List<Item> items= new ArrayList<>();
private static String apiURL="https://cookit.proxy.beeceptor.com/items";


static {
	String result= new RestTemplate().getForObject(apiURL,String.class);
	Item[] itemsArray;
	try {
		itemsArray = new ObjectMapper().readValue(result,Item[].class);
		List<Item> itemsList= Arrays.asList(itemsArray);
		items.addAll(itemsList);
	} catch (JsonMappingException e) {
		e.printStackTrace();
	} catch (JsonProcessingException e) {
		e.printStackTrace();
	}	
}

public Item findItemWithId(int id) {
	for(Item item: items) {
		if(item.getId()==id) {
			return item;
		}
	}
	return null;
}

public List<Item> retrieveItems(){
	return items;
}

public void addItem(Item item) {
	items.add(item);
}

public void removeItem(int id) {
	Iterator<Item> iterator =items.iterator();
	while(iterator.hasNext()) {
		Item itemTemp =iterator.next();
		if(itemTemp.getId()==id) {
			iterator.remove();
		}
	}
}
	
}
