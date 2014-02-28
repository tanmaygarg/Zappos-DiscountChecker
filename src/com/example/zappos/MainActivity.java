package com.example.zappos;


import java.util.Calendar;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainActivity extends Activity {

	Button searchButton;
	Button button1;
	ToggleButton toggleButton1;
	EditText searchEditText;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.activity_main);
		searchButton = (Button) findViewById(R.id.search_button);
		button1 = (Button) findViewById(R.id.button1);
		toggleButton1= (ToggleButton) findViewById(R.id.toggleButton1);
		searchEditText = (EditText) findViewById(R.id.search_edit_text);
		Log.v("main", "activity started");

		searchButton.setOnClickListener(new OnClickListener() {            
			@Override
			public void onClick(View v) {
				if(isNetworkConnected()){
				String query = searchEditText.getText().toString();
				if(query.equals("")) 				longToast("Please enter something to find");
				else{
				longToast("Searching for : "+query);
				Intent intent = new Intent(MainActivity.this, DisplayGrid.class);
	            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	            intent.putExtra("query", query);
	            getApplicationContext().startActivity(intent);}
				}
				else 				longToast("Unable to Connect to Internet");

			}

			
		});
		
		button1.setOnClickListener(new OnClickListener() {            

			public void onClick(View v) {

				Intent intent = new Intent(MainActivity.this, DiscountChecker.class);
	            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	            getApplicationContext().startActivity(intent);
			    Log.v("main","called DiscountCheckService");
			}
		});
		
		toggleButton1.setOnCheckedChangeListener(new OnCheckedChangeListener(){

		
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				if(toggleButton1.isChecked()){
					Calendar cal = Calendar.getInstance();

					Intent intent = new Intent(MainActivity.this, DiscountCheckerService.class);
					PendingIntent pintent = PendingIntent.getService(MainActivity.this, 0, intent, 0);

					AlarmManager alarm = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
					//get notifications every 15 minutes
					alarm.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), 900*1000, pintent); 
				}
				else{
					stopService(new Intent(MainActivity.this, DiscountCheckerService.class));
				
				}
			}
				
				});
	}

	private boolean isNetworkConnected() {
		  ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		  NetworkInfo ni = cm.getActiveNetworkInfo();
		  if (ni == null) {
		   // There are no active networks.
		   return false;
		  } else
		   return true;
		 }
	public void longToast(CharSequence message) {
		Toast.makeText(this, message, Toast.LENGTH_LONG).show();
	}
	

}
