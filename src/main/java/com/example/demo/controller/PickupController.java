package com.example.demo.controller;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.core.metrics.StartupStep.Tags;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.demo.model.Item;
import com.example.demo.model.Protein;
import com.example.demo.services.ItemsService;
import com.example.demo.services.ProteinsService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class PickupController implements ErrorController{

	@Autowired
	ItemsService itemsService;
	
	@Autowired
	ProteinsService proteinsService;
	
	@GetMapping("/ping")
	public ResponseEntity<String> pingResponse() {
		String s= "{\r\n" + 
				"\"success\": true\r\n" + 
				"}";
		return new ResponseEntity<>(s,HttpStatus.OK);
	}
	
	@GetMapping("/items") //http://localhost:8080/items
	public ResponseEntity<String> responseItems(String requestBody) {
		List<Item> items= itemsService.retrieveItems();
		System.out.println(items.toString());
		final HttpHeaders header=new HttpHeaders();
		header.setContentType(MediaType.APPLICATION_JSON);
		String response="";
		try {
			response = new ObjectMapper().writeValueAsString(items);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ResponseEntity(response,header,HttpStatus.OK);
	}
	
	@GetMapping("/proteins") //http://localhost:8080/proteins
	public ResponseEntity<String> responseProteins(String requestBody) {
		List<Protein> proteins= proteinsService.retrieveProteins();
		System.out.println(proteins.toString());
		final HttpHeaders header=new HttpHeaders();
		header.setContentType(MediaType.APPLICATION_JSON);
		String response="";
		try {
			response = new ObjectMapper().writeValueAsString(proteins);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ResponseEntity(response,header,HttpStatus.OK);
	}
	
	@PostMapping("/createbox") //http://localhost:8080/createbox
	public ResponseEntity<String> createBox(@RequestBody String requestBody){
		List<Item> itemsList =itemsService.retrieveItems();
		System.out.println("got new request: "+requestBody);
		JSONArray  jsonArray=new JSONObject(requestBody).getJSONArray("item-ids");
		List<Item> pickItems=new ArrayList<>();
		List<Item> outOfStock=new ArrayList<>();
		List<String> picksArray= new ArrayList<>(); // As size is not fix we will convert later into Array
		List<String> outOfStockArray= new ArrayList<>(); //we will convert into Array
		System.out.println("picks empty array"+picksArray.toString());
		System.out.println("oos empty array"+outOfStock.toString());
		for(int i=0;i<jsonArray.length();i++) {
		System.out.println(jsonArray.getInt(i)+" ");
		Item item =itemsService.findItemWithId(jsonArray.getInt(i));
		if(item!=null&&item.isInStock()) {
			pickItems.add(item);
			String stations=item.getStation()+":";
			if(item.containsMeat()) {
				stations+=proteinsService.findProteinWithCode(item.getMeatCode()).getStation();
			}
			for(String s: stations.split(":")) {
				picksArray.add(s);
			}

		}else {
			outOfStock.add(item);
			String stations=item.getStation()+":";
			if(item.containsMeat()) {
				stations+=proteinsService.findProteinWithCode(item.getMeatCode()).getStation();
			}
			for(String s: stations.split(":")) {
				outOfStockArray.add(s);
			}
		}
		}
		JSONObject responseJSON=new JSONObject().accumulate("picks", picksArray.toArray()).accumulate("out-of-stock",outOfStockArray.toArray());
		
		final HttpHeaders header=new HttpHeaders();	 
		header.setContentType(MediaType.APPLICATION_JSON);
		return new ResponseEntity(responseJSON.toString(),header,HttpStatus.OK);
	}
	
	
	
	@RequestMapping("/error")
	public ResponseEntity<String> error(@RequestParam String tags,@RequestParam String sortBy,@RequestParam String direction){
		String s="";
		if(tags==null) {
			s= "{\r\n" + 
				"\"error\":"+"Tags parameter is required"+" \r\n" + 
				"}";
		}else if(!sortBy.equalsIgnoreCase("id")||!sortBy.equalsIgnoreCase("reads")||!sortBy.equalsIgnoreCase("likes")||!sortBy.equalsIgnoreCase("popularity")){
			s= "{\r\n" + 
					"\"error\":"+"sortBy parameter is invalid"+" \r\n" + 
				"}";
		}else if(!direction.equalsIgnoreCase("asc")||!direction.equalsIgnoreCase("desc")) {
			s= "{\r\n" + 
					"\"error\":"+"direction parameter is invalid"+" \r\n" + 
				"}";
		}
				return new ResponseEntity<>(s,HttpStatus.BAD_REQUEST);
	}
}
