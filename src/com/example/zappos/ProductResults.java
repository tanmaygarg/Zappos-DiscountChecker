package com.example.zappos;

import java.util.ArrayList;
import android.util.Log;

public class ProductResults extends GenericSearch<Product>{

	// wrapper to the private method
	public ArrayList<Product> find(String query){
		return retrieveProductList(query);
	}

	private ArrayList<Product> retrieveProductList(String query){
		String url = constructSearchUrl(query);
		String response = httpReceiver.retrieve(url);
		Log.d(getClass().getSimpleName(), response);
		//if(response!=null)
		return jsonParser.parser(response);
	}

	@Override
	public Product find(String query, String query1) {
		// TODO Auto-generated method stub
		return null;
	}
}
