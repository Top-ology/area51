package com.imc.amsterdam.orderbook;

public class OrderFactory 
{
	public IOrder getLimitOrder(int brokerId, Side side, long price, int quantity, long timestamp) {
		return new Order(brokerId, OrderType.LIMIT, side, price, quantity, timestamp); 
	}
	
	public IOrder getMarketOrder(int brokerId, Side side, int quantity, long timestamp) {
		return new Order(brokerId, OrderType.MARKET, side, -1L, quantity, timestamp); 
	}
}
