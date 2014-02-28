package com.example.zappos;

import java.util.Map;


import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

public class DiscountCheckerService extends Service{

	public void onCreate() {
		Toast.makeText(this, "The new Service has been Created.\n\nSelected Products will be checked every 15 minutes for discounts", Toast.LENGTH_LONG).show();

	}
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		//TODO do something useful
		SharedPreferences pref = getBaseContext().getSharedPreferences("zappospref", Context.MODE_PRIVATE);
		Log.v("main","Service Started");

		Map<String, ?> map = pref.getAll();
		if(map==null)   					{}
		else{
			for(String s: map.keySet())
			{				RetrieveSearchRes2 res = new RetrieveSearchRes2(getBaseContext());

			Log.v("main",s+" "+map.get(s));
			res.execute(s,(String)map.get(s));
			}
		}




		return Service.START_NOT_STICKY;
	}



	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public void onDestroy() {
		Toast.makeText(this, "Service Destroyed", Toast.LENGTH_LONG).show();

	}
} 
class RetrieveSearchRes2 extends AsyncTask<String, Void, Product> {
	private GenericSearch<Product> getDiscountFromProductId;
	Context context;
	public RetrieveSearchRes2(Context context)
	{
		this.context=context;
	}
	protected Product doInBackground(String... params) {
		String query = params[0];
		String query1 = params[1];
		getDiscountFromProductId = new GetDiscountFromProductId();
		return getDiscountFromProductId.find(query, query1);
	}

	@Override
	protected void onPostExecute(Product result) { 
		Log.v("main",result.percentOff);
		String percentOff =result.percentOff;
		if(percentOff.equals("")){
			Log.v("main", "PercentOff is ull");
		}
		else{
			percentOff = percentOff.substring(0, percentOff.length()-1);
			int pOff = Integer.parseInt(percentOff);
			final String productName = result.productName;
			final String price = result.price;
			final String percentOff1 = result.percentOff;
			if(pOff>=20)
			{	
				NotificationCompat.Builder mBuilder =
						new NotificationCompat.Builder(context)
				.setSmallIcon(R.drawable.ic_launcher)
				.setContentTitle("Discount on Product")
				.setContentText("Hi, There is a discount of "+ percentOff1+" on the product "+productName+"\nPrice: "+price);
				Intent resultIntent = new Intent(context, DiscountChecker.class);

				TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
				// Adds the back stack for the Intent (but not the Intent itself)
				stackBuilder.addParentStack(MainActivity.class);
				// Adds the Intent that starts the Activity to the top of the stack
				stackBuilder.addNextIntent(resultIntent);
				PendingIntent resultPendingIntent =
						stackBuilder.getPendingIntent(
								0,
								PendingIntent.FLAG_UPDATE_CURRENT
								);
				mBuilder.setContentIntent(resultPendingIntent);
				NotificationManager mNotificationManager =
						(NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
				// mId allows you to update the notification later on.
				mNotificationManager.notify(1021, mBuilder.build());
			}
		}
	}
}