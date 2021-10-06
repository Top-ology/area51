package com.imc.amsterdam.orderbook;

public class OrderBook implements IOrderBook
{
	private IPriceLevels bids = new PriceLevels();
	private IPriceLevels asks = new PriceLevels();

	public void addOrder(Side side, IOrder order) {
		switch(side) {
			case BUY :
				bids.addOrder(order);
				break;
			case SELL:
				asks.addOrder(order);
		}
	}

	public OrderBook() {
		clear();
	}

	public void cancelOrder(Side side, int orderId) {
		switch(side) {
			case BUY :
				if(bids.containsOrder(orderId)) {
					bids.removeOrder(orderId);
				}
				break;
			case SELL:
				if(asks.containsOrder(orderId)) {
					asks.removeOrder(orderId);
				}
		}
	}

	@Override
	public void clear() {
		bids.clear();
		asks.clear();
	}

	@Override
	public IPriceLevels getAsks() {
		return asks;
	}

	@Override
	public IPriceLevels getBids() {
		return bids;
	}

	@Override
	public void replaceOrder(int orderId, IOrder newOrder) {

	}
}
