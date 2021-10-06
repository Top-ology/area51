package com.imc.amsterdam.orderbook;

public interface ITrade {
 int getOrderId();
	public long getPrice();
	public int getQuantity();
	public int getBuyerId();
	public int getSellerId();
	public long getTimestamp();
}
