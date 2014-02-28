package com.example.zappos;

import java.util.ArrayList;

import android.util.Log;

public class GetDiscountFromProductId extends GenericSearch<Product>{
	// wrapper to the private method
		public Product find(String productId, String styleId){
			return retrieveProductList(productId,styleId);
		}

		private Product retrieveProductList(String productId, String styleId){
			String url = constructSearchUrl(productId);
			String response = httpReceiver.retrieve(url);
			Log.d(getClass().getSimpleName(), response);
			//if(response!=null)
			return jsonParser.parser(response,styleId);
		}

		@Override
		public ArrayList<Product> find(String query) {
			// TODO Auto-generated method stub
			return null;
		}
	}


