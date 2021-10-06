package com.imc.amsterdam.orderbook;

import java.util.List;

public interface IOrderReport {
	IOrder getOrder();
	List<Trade> getTrades();
	boolean isEmpty();
	int size();

}
