package com.imc.amsterdam.orderbook;

public class Trade implements ITrade
{
	private int orderId;
	private long price;
	private int quantity;
	private int buyerId;
	private int sellerId;
	private long timestamp;

	public Trade(int orderId, long price, int quantity, int buyerId, int sellerId, long timestamp) {
		this.orderId = orderId;	this.price = price;	this.quantity = quantity; this.buyerId = buyerId; this.sellerId = sellerId; this.timestamp = timestamp;
	}
	
	public int getBuyerId() { 
		return buyerId; 
	}
	
	public int getOrderId() { 
		return orderId; 
	}
	
	public long getPrice() { 
		return price; 
	}
		
	public int getQuantity() { 
		return quantity; 
	}
	
	public int getSellerId() { 
		return sellerId; 
	}
	
	public long getTimestamp() { 
		return timestamp; 
	}
}
