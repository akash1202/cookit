package com.example.demo.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.demo.model.Item;
import com.example.demo.model.Protein;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ProteinsService {
	private static List<Protein> proteins= new ArrayList<>();
	private static String apiURL="https://cookit.proxy.beeceptor.com/proteins";


	static {
		String result= new RestTemplate().getForObject(apiURL,String.class);
		Protein[] proteinsArray;
		try {
			proteinsArray = new ObjectMapper().readValue(result,Protein[].class);
			List<Protein> proteinsList= Arrays.asList(proteinsArray);
			proteins.addAll(proteinsList);
		} catch (JsonMappingException e) {
			System.out.println(ProteinsService.class.getSimpleName()+": Error in Mapping");
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			System.out.println(ProteinsService.class.getSimpleName()+": Error in JSON processing");
			e.printStackTrace();
		}
	}

	public Protein findProteinWithCode(char code) {
		for(Protein protein: proteins) {
			if(protein.getCode().equalsIgnoreCase(code+"")) {
				return protein;
			}
		}
		return null;
	}
	public List<Protein> retrieveProteins(){
		return proteins;
	}

	public void addProtein(Protein protein) {
		proteins.add(protein);
	}

	public void removeProtein(int code) {
		Iterator<Protein> iterator =proteins.iterator();
		while(iterator.hasNext()) {
			Protein proteinTemp =iterator.next();
			if(proteinTemp.getCode().equals(code)) {
				iterator.remove();
			}
		}
	}
}
