package com.imc.amsterdam.orderbook;

public class OrderLibrary
{
	public static boolean isLimitOrder(IOrder order) {
		return order.getOrderType() == OrderType.LIMIT;
	}
	
	public static boolean isMarketOrder(IOrder order) {
		return order.getOrderType() == OrderType.MARKET;
	}
}
