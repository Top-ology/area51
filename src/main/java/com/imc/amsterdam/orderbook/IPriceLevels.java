package com.imc.amsterdam.orderbook;

public interface IPriceLevels {
	void addOrder(IOrder order);
	void clear();
	boolean containsOrder(int orderId);
	boolean containsPrice(long price);
	IOrderList getOrderList(long price);
	long getMaxPrice();
	IOrderList getMaxPriceOrderList();
	IOrderList getMinPriceOrderList();
	long getMinPrice();
	int getVolume();
	void removeOrder(int orderId);
	int size();
	void updateQuantity(int orderId, int newQuantity);
}
