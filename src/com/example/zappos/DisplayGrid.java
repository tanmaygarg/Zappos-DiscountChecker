package com.example.zappos;

import java.util.ArrayList;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class DisplayGrid extends Activity{
	/** Called when the activity is first created. */
	//		HashMap<Integer,Integer> checkMap = new HashMap<Integer,Integer> (); 
	private GenericSearch<Product> productResults = new ProductResults();
	private ArrayList<Product> productList;
	private Button button1;
	private Button button2;

	String query;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.grid_layout);
		query = getIntent().getStringExtra("query");
		GenericSearch.PAGE_NUM=1;
		// prepared arraylist and passed it to the Adapter class
		RetrieveSearchResults task = new RetrieveSearchResults();
		task.execute(query);

		button1 = (Button) findViewById(R.id.button1);
		button2 = (Button) findViewById(R.id.button2);

		button1.setOnClickListener(new Button.OnClickListener() {            
			@Override
			public void onClick(View v) {
				if(GenericSearch.PAGE_NUM<=(JsonParser.totalResultCount/15)){
				GenericSearch.PAGE_NUM++;
				RetrieveSearchResults task = new RetrieveSearchResults();
				task.execute(query);
				}
				else Toast.makeText(DisplayGrid.this, "No more items", Toast.LENGTH_SHORT).show();

			}
		});
		button2.setOnClickListener(new Button.OnClickListener() {            
			@Override
			public void onClick(View v) {
				if(GenericSearch.PAGE_NUM>1){
				GenericSearch.PAGE_NUM--;
				RetrieveSearchResults task = new RetrieveSearchResults();
				task.execute(query);
				}
				else Toast.makeText(DisplayGrid.this, "Reached 1st Page", Toast.LENGTH_SHORT).show();

			}
		});
	} 


	class RetrieveSearchResults extends AsyncTask<String, Void, ArrayList<Product>> {
		private GridviewAdapter mAdapter;
		private GridView gridView;
		ProgressDialog progDailog = new ProgressDialog(DisplayGrid.this);
		protected void onPreExecute() {
            super.onPreExecute();
            progDailog.setMessage("Loading...");
            progDailog.setIndeterminate(false);
            progDailog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progDailog.setCancelable(true);
            progDailog.show();
        }
		protected ArrayList<Product> doInBackground(String... params) {
			String query = params[0];
			Log.v("main", "inside async");
			productList = productResults.find(query);
			return productList;

		}

		@Override
		protected void onPostExecute(final ArrayList<Product> result) {     

			runOnUiThread(new Runnable() {
				@Override
				public void run() {			
					progDailog.dismiss();
					mAdapter = new GridviewAdapter(DisplayGrid.this,result);
					// Set custom adapter to gridview
					gridView = (GridView) findViewById(R.id.gridView1);
					gridView.setAdapter(mAdapter);
					// Implement On Item click listener
					gridView.setOnItemClickListener(new OnItemClickListener() 
					{
						@Override
						public void onItemClick(AdapterView<?> arg0, View arg1, int position,
								long arg3) {
							Toast.makeText(DisplayGrid.this, mAdapter.getItem(position), Toast.LENGTH_SHORT).show();
							Intent intent = new Intent(DisplayGrid.this, DisplayProduct.class);
							intent.putExtra("bitmap",mAdapter.getBitmap(position));
							intent.putExtra("name",mAdapter.getItem(position));
							intent.putExtra("productId",mAdapter.getProductId(position));
							intent.putExtra("styleId",mAdapter.getStyleId(position));
							intent.putExtra("percentOff",mAdapter.getItemDiscount(position));
							intent.putExtra("price",mAdapter.getPrice(position));
							intent.putExtra("productUrl",mAdapter.getUrl(position));
							intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
							getApplicationContext().startActivity(intent);
						}
					});
				}
			});

		}

	}


}

