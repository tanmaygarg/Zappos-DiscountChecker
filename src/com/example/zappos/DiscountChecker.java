package com.example.zappos;

import java.util.Map;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;



public class DiscountChecker extends Activity{

static public int i=1;
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		 setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	        setContentView(R.layout.discounted_products);
		SharedPreferences pref = getBaseContext().getSharedPreferences("zappospref", Context.MODE_PRIVATE);
		Log.v("main","Service Started");

		Map<String, ?> map = pref.getAll();
		if(map==null)   					Toast.makeText(DiscountChecker.this, "Please add a Product first", Toast.LENGTH_SHORT).show();

		else{
			for(String s: map.keySet())
			{				RetrieveSearchRes1 res = new RetrieveSearchRes1();

			Log.v("main",s+" "+map.get(s));
			res.execute(s,(String)map.get(s));
			}
		}
	}

	class RetrieveSearchRes1 extends AsyncTask<String, Void, Product> {
		private GenericSearch<Product> getDiscountFromProductId;
		ProgressDialog progDailog = new ProgressDialog(DiscountChecker.this);
		protected void onPreExecute() {
			super.onPreExecute();
			progDailog.setMessage("Loading...");
			progDailog.setIndeterminate(false);
			progDailog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			progDailog.setCancelable(true);
			progDailog.show();
		}
		protected Product doInBackground(String... params) {
			String query = params[0];
			String query1 = params[1];
			getDiscountFromProductId = new GetDiscountFromProductId();
			return getDiscountFromProductId.find(query, query1);
		}

		@Override
		protected void onPostExecute(Product result) { 
			progDailog.dismiss();
			Log.v("main",result.percentOff);
			String percentOff =result.percentOff;
			if(percentOff.equals("")){
				Log.v("main", "PercentOff is ull");
			}
			else{
				percentOff = percentOff.substring(0, percentOff.length()-1);
				int pOff = Integer.parseInt(percentOff);
				final String productUrl = result.productUrl;
				if(pOff>=20)
				{	int i=DiscountChecker.i++;
					Button myButton = new Button(DiscountChecker.this);
			        myButton.setText(result.brandName+": "+result.productName+" "+result.percentOff);
			        myButton.setId(i);
			        @SuppressWarnings("unused")
					final int id_ = myButton.getId();

			        LinearLayout layout = (LinearLayout) findViewById(R.id.linearLayout1);
			        layout.addView(myButton);

			        myButton.setOnClickListener(new View.OnClickListener() {
			            public void onClick(View view) {
			            	Toast.makeText(DiscountChecker.this,
			                        "Keep Pressed to open product ", Toast.LENGTH_SHORT)
			                        .show();
			            }
			        });
			        
			        myButton.setOnLongClickListener(new View.OnLongClickListener() {
			            public boolean onLongClick(View view) {
			            	 Uri uri = Uri.parse(productUrl);
							 Intent intent = new Intent(Intent.ACTION_VIEW, uri);
							 startActivity(intent);
							return false;
			            }

			        });
				}
			}

		}
	}
}
