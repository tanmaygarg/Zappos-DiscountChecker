package com.example.zappos;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.util.Log;

public class HttpReceiver {

	// Creating HTTP client
	HttpClient httpClient = new DefaultHttpClient();

	public String retrieve(String url){

		HttpGet getRequest = new HttpGet(url);
		try {

			HttpResponse getResponse = httpClient.execute(getRequest);
			final int statusCode = getResponse.getStatusLine().getStatusCode();
			if (statusCode != HttpStatus.SC_OK) { 
				Log.w(getClass().getSimpleName(), "Error " + statusCode + " for URL " + url); 
				return null;
			}
			HttpEntity getResponseEntity = getResponse.getEntity();
			if (getResponseEntity != null) {    	
				
				Log.v("main", "Got the HTTP response");

			return EntityUtils.toString(getResponseEntity);
			}
			else     	Log.v("main", "null response");

		}
		catch(Exception e){
			Log.w(getClass().getSimpleName(), "Error for URL " + url, e);
		}
		return null;
	}

}
