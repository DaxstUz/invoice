package com.invoice.word;

public class ShopsDetailBean {
	
	

	private String shopName;
	
	private int shopSl;
	
	private float price;
	
	
	public ShopsDetailBean( String shopName,int shopSl ,float price) {
		this.shopName=shopName;
		this.shopSl=shopSl;
		this.price=price;
	}


	public String getShopName() {
		return shopName;
	}


	public void setShopName(String shopName) {
		this.shopName = shopName;
	}


	public int getShopSl() {
		return shopSl;
	}


	public void setShopSl(int shopSl) {
		this.shopSl = shopSl;
	}


	public float getPrice() {
		return price;
	}


	public void setPrice(float price) {
		this.price = price;
	}

	
	
}
