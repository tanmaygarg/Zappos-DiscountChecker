package com.example.zappos;


import android.graphics.Bitmap;


public class Product {
	public String styleId;
	public String productId; 
	public String brandName; 
	public String productName; 
	public String thumbnailImageUrl; 
	public String originalPrice; 
	public String price; 
	public String percentOff; 
	public String productUrl;
	public Bitmap productImage;

	public Product(String styleId, String productId,String brandName,String productName,String thumbnailImageUrl,String originalPrice,String price,String percentOff,String productUrl, Bitmap productImage)
	{
		this.styleId=styleId;
		 this.productId=productId; 
		 this.brandName=brandName; 
		 this.productName=productName; 
		 this.thumbnailImageUrl=thumbnailImageUrl; 
		 this.originalPrice=originalPrice; 
		 this.price=price; 
		 this.percentOff=percentOff; 
		 this.productUrl=productUrl;
		 this.productImage=productImage;

		
	}
}