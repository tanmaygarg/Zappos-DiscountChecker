package com.example.zappos;

import java.util.HashMap;


public class ProductIdResult extends GenericProductIdSearch<HashMap<String,String>>{

	
	public HashMap<String,String> find(String query){
		return retrieveProduct(query);
	}
	private  HashMap<String,String> retrieveProduct(String query){
		String url = constructSearchUrl(query);
		String response = httpReceiver.retrieve(url);
		return jsonParser.productIdParser(response);
	}
}
