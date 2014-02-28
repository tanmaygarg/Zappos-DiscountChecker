package com.example.zappos;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;



public class DisplayProduct<T> extends Activity{
	String name;
	String productId;
	String styleId;
	static String percentOff;
	static String price;
	static String productUrl;
	HashMap<String,String> hash= new HashMap<String,String>();
	static HashMap<String,String> checkDiscountMap= new HashMap<String,String>();
	private GenericProductIdSearch<HashMap<String,String>> productIdResult = new ProductIdResult();

	 public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	        setContentView(R.layout.product_view);
	        Button button1=(Button) findViewById(R.id.button1);
	        // Set custom adapter to gridview
	         Bundle bundle = getIntent().getExtras();
	         name = (String) bundle.get("name");
	         productId = (String) bundle.get("productId");
	         styleId = (String) bundle.get("styleId");
	         percentOff = (String) bundle.get("percentOff");
	         price = (String) bundle.get("price");
	         productUrl = (String) bundle.get("productUrl");
	         RetrieveSearchRes task = new RetrieveSearchRes();
				task.execute(productId);	
				RetrieveBitmap bmp = new RetrieveBitmap();
				try {
					bmp.execute(task.get().get("defaultImageUrl"));
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			button1.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					Toast.makeText(DisplayProduct.this, "Product added", Toast.LENGTH_SHORT).show();
					checkDiscountMap.put(productId, styleId);
			        SharedPreferences pref = getBaseContext().getSharedPreferences("zappospref", Context.MODE_PRIVATE);
			        SharedPreferences.Editor editor = pref.edit();    
				    editor.putString(productId, styleId);
				    editor.commit();

				}
				
			});
	 }   
	 
	 class RetrieveSearchRes extends AsyncTask<String, Void, HashMap<String,String>> {
		 TextView textview1 =(TextView) findViewById(R.id.textView1);
		 TextView textview2 =(TextView) findViewById(R.id.textView2);
		 TextView textview3 =(TextView) findViewById(R.id.textView3);
			ProgressDialog progDailog = new ProgressDialog(DisplayProduct.this);
			protected void onPreExecute() {
	            super.onPreExecute();
	            progDailog.setMessage("Loading...");
	            progDailog.setIndeterminate(false);
	            progDailog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
	            progDailog.setCancelable(true);
	            progDailog.show();
	        }
			protected HashMap<String,String> doInBackground(String... params) {
				String query = params[0];
				hash = productIdResult.find(query);
				return hash;

			}

			@Override
			protected void onPostExecute(HashMap<String,String> result) { 
				progDailog.dismiss();
				Log.v("main",result.get("productName"));
				textview1.setText(result.get("brandName")+": "+result.get("productName"));
				textview2.setText("Price: "+DisplayProduct.price);
				textview3.setText("Discount: "+DisplayProduct.percentOff);
				
			}
	 }
	 class RetrieveBitmap extends AsyncTask<String, Void, Bitmap> {
		 ImageButton imageview =(ImageButton) findViewById(R.id.imageView1);
			ProgressDialog progDailog = new ProgressDialog(DisplayProduct.this);
			Bitmap bitmap;
			
			protected Bitmap doInBackground(String... params) {
				String query = params[0];
				bitmap = downloadImage(query);
				return bitmap;

			}

			@Override
			protected void onPostExecute(Bitmap result) { 
				progDailog.dismiss();
				imageview.setImageBitmap(result);
				imageview.setOnLongClickListener(new OnLongClickListener(){

					@Override
					public boolean onLongClick(View arg0) {
						// TODO Auto-generated method stub
						 Uri uri = Uri.parse(DisplayProduct.productUrl);
						 Intent intent = new Intent(Intent.ACTION_VIEW, uri);
						 startActivity(intent);
						return false;
					}
					
				});
				imageview.setOnClickListener(new OnClickListener(){

					@Override
					public void onClick(View arg0) {
						Toast.makeText(DisplayProduct.this, "Long press to open product", Toast.LENGTH_SHORT).show();
						
					}
					
				});
			}
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
