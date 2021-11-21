package com.example.demo.model;

import java.util.regex.Pattern;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Data
public class Item {
 private int id;
 private String name;
 private String displayName;
 private int volume;
 private String deliveryWeek;
 private String station;
 private String category;
 
 public Item() {}
 
 


public boolean containsMeat() {
	if(!category.equalsIgnoreCase("RECETTE"))
		return false;
	 if(Pattern.matches("[0-9]*[A-Z]-[A-Z]-[0-9]*", displayName))
		 return true;
	 return false;
 }
 
 public Character getMeatCode() {
	 return displayName.charAt(displayName.indexOf('-')+1);
 }
 
 public boolean isInStock() {
	 return volume>=1;
 }
}
