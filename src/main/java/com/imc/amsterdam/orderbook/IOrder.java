package com.imc.amsterdam.orderbook;

public interface IOrder {
	int getOrderId();
	OrderType getOrderType();
	Side getSide();
	long getPrice();
	int getQuantity();
	int getBrokerId();
	long getTimestamp();
	IOrderList getOrderList();
	IOrder getNext();
	IOrder getPrev();
	void setOrderId(int orderId);
	void setQuantity(int quantity);
	void setOrderList(IOrderList orderList);
	void setNext(IOrder order);
	void setPrev(IOrder order);
}
