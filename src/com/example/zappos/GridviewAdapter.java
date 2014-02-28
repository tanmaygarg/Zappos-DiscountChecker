package com.example.zappos;

import java.util.ArrayList;

import android.app.Activity;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class GridviewAdapter extends BaseAdapter
{
	private ArrayList<Product> productList;
	private Activity activity;
	
	public GridviewAdapter(Activity activity,ArrayList<Product> productList) {
		super();
		this.productList = productList;
		this.activity = activity;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return productList.size();
	}

	@Override
	public String getItem(int position) {
		// TODO Auto-generated method stub
		return productList.get(position).productName;
	}
	
	public Bitmap getBitmap(int position) {
		// TODO Auto-generated method stub
		return productList.get(position).productImage;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}
	public String getProductId(int position) {

	return productList.get(position).productId;
	}
	public String getItemDiscount(int position) {
		// TODO Auto-generated method stub
		return productList.get(position).percentOff;
	}
	public String getStyleId(int position) {
		// TODO Auto-generated method stub
		return productList.get(position).styleId;
	}
	public String getPrice(int position) {
		// TODO Auto-generated method stub
		return productList.get(position).price;
	}
	public String getUrl(int position) {
		// TODO Auto-generated method stub
		return productList.get(position).productUrl;
	}

	public static class ViewHolder
	{
		public ImageView imgViewFlag;
		public TextView txtViewTitle;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder view;
		LayoutInflater inflator = activity.getLayoutInflater();
		
		if(convertView==null)
		{
			view = new ViewHolder();
			convertView = inflator.inflate(R.layout.gridview_row, null);
			
			view.txtViewTitle = (TextView) convertView.findViewById(R.id.textView1);
			view.imgViewFlag = (ImageView) convertView.findViewById(R.id.imageView1);
			
			convertView.setTag(view);
		}
		else
		{
			view = (ViewHolder) convertView.getTag();
		}
		
		view.txtViewTitle.setText(productList.get(position).productName);
		view.imgViewFlag.setImageBitmap(productList.get(position).productImage);
		
		return convertView;
	}

}
