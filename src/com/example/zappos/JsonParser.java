package com.example.zappos;


import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class JsonParser {

	JSONArray results=null;
	ArrayList<Product> resultList;
	public static int totalResultCount;
	HashMap<String,String> hash;
	public ArrayList<Product> parser(String string)
	{
		resultList = new ArrayList<Product>();
		Log.v("main", "json started");
		try {
			JSONObject jsonObj = new JSONObject(string);
			totalResultCount = jsonObj.getInt("totalResultCount");
			results = jsonObj.getJSONArray("results");
			JSONObject res=null;
			for (int i = 0; i < results.length(); i++) {
				res = results.getJSONObject(i);
				Product product = new Product(
				res.getString("styleId"),
				res.getString("productId"),
				res.getString("brandName"),
				res.getString("productName"),
				res.getString("thumbnailImageUrl"),
				res.getString("originalPrice"),
				res.getString("price"),
				res.getString("percentOff"),
				res.getString("productUrl")
				,downloadImage(res.getString("thumbnailImageUrl")));
				resultList.add(product);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			Log.v("main", e.toString());

		}
		Log.v("main", "json parsed");
		return resultList;
	}
	
	public Product parser(String string, String styleId)
	{
		Product product=null;
		Log.v("main", "json started");
		try {
			JSONObject jsonObj = new JSONObject(string);
			totalResultCount = jsonObj.getInt("totalResultCount");
			results = jsonObj.getJSONArray("results");
			JSONObject res=null;
			for (int i = 0; i < results.length(); i++) {
				res = results.getJSONObject(i);
				if(res.getString("styleId").equals(styleId)){
			    product = new Product(
				res.getString("styleId"),
				res.getString("productId"),
				res.getString("brandName"),
				res.getString("productName"),
				res.getString("thumbnailImageUrl"),
				res.getString("originalPrice"),
				res.getString("price"),
				res.getString("percentOff"),
				res.getString("productUrl")
				,downloadImage(res.getString("thumbnailImageUrl")));
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			Log.v("main", e.toString());

		}
		Log.v("main", "json parsed");
		return product;
	}
	
	
	public HashMap<String,String> productIdParser(String string)
	{
		hash = new HashMap<String,String> ();
		try {
			JSONObject jsonObj = new JSONObject(string);
			hash.put("statusCode", jsonObj.getString("statusCode"));
			results = jsonObj.getJSONArray("product");
			hash.put("brandName", results.getJSONObject(0).getString("brandName"));
			hash.put("productId", results.getJSONObject(0).getString("productId"));
			hash.put("productName", results.getJSONObject(0).getString("productName"));
			hash.put("defaultProductUrl", results.getJSONObject(0).getString("defaultProductUrl"));
			hash.put("defaultImageUrl", results.getJSONObject(0).getString("defaultImageUrl"));
			}
		 catch (JSONException e) {
			// TODO Auto-generated catch block
			Log.v("main", e.toString());

		}
		Log.v("main", "json parsed");
		return hash;
	}
	
	private Bitmap downloadImage(String url) {
		Bitmap bitmap = null;
		InputStream stream = null;
		BitmapFactory.Options bmOptions = new BitmapFactory.Options();
		bmOptions.inSampleSize = 1;

		try {
			stream = getHttpConnection(url);
			bitmap = BitmapFactory.
					decodeStream(stream, null, bmOptions);
			stream.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return bitmap;
	}
	private InputStream getHttpConnection(String urlString)
			throws IOException {
		InputStream stream = null;
		URL url = new URL(urlString);
		URLConnection connection = url.openConnection();

		try {
			HttpURLConnection httpConnection = (HttpURLConnection) connection;
			httpConnection.setRequestMethod("GET");
			httpConnection.connect();

			if (httpConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
				stream = httpConnection.getInputStream();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return stream;
	}
}
