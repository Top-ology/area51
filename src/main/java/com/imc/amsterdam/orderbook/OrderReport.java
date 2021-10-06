package com.imc.amsterdam.orderbook;

import java.util.List;

public class OrderReport implements IOrderReport
{
	private final IOrder order;
	private final List<Trade> trades;

	public OrderReport(IOrder order, List<Trade> trades) {
		this.order = order; this.trades = trades; 
	}
	
	public IOrder getOrder() {
		return order;
	}

	public List<Trade> getTrades() {
		return trades;
	}
	
	public boolean isEmpty() {
		return size() == 0;
	}
	
	public int size() {
		return trades != null ? trades.size() : 0;
	}
}
