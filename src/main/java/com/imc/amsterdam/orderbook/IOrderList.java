package com.imc.amsterdam.orderbook;

public interface IOrderList {
	void add(IOrder order);
	int getVolume();
	IOrder getHead();
	IOrder getTail();
	void remove(IOrder order);
	void setVolume(int volume);
	int size();
}
