package com.imc.amsterdam.orderbook;

import java.util.TreeMap;
import java.util.HashMap;

public class PriceLevels implements IPriceLevels
{
	private final HashMap<Long, IOrderList> priceMap = new HashMap<>();	
	
	private final TreeMap<Long, IOrderList> priceTree = new TreeMap<>();
	
	private final HashMap<Integer, IOrder> orderMap = new HashMap<>();
	
	private int volume;
	
	public PriceLevels() {
		clear();
	}

	private void addNewOrderList(long price) {
		IOrderList newList = new OrderList();
		priceTree.put(price, newList);
		priceMap.put(price, newList);
	}
	
	public void addOrder(IOrder order) {
		int orderId = order.getOrderId();
		long price = order.getPrice();
		if (containsOrder(orderId)) {
			removeOrder(orderId);
		}
		if (!containsPrice(price)) {
			addNewOrderList(price);
		}
		order.setOrderList(priceMap.get(price));
		priceMap.get(price).add(order);
		orderMap.put(orderId, order);
		volume += order.getQuantity();
	}

	public void clear() {
		priceTree.clear();
		priceMap.clear();
		orderMap.clear();
		volume = 0;
	}
	
	public boolean containsOrder(int orderId) {
		return orderMap.containsKey(orderId);
	}

	public boolean containsPrice(long price) {
		return priceMap.containsKey(price);
	}

	public long getMaxPrice() {
		return !priceTree.isEmpty() ? priceTree.lastKey() : -1;
	}

	public IOrderList getMaxPriceOrderList() {
		return !priceTree.isEmpty() ? getOrderList(getMaxPrice()) : null;
	}

	public long getMinPrice() {
		return !priceTree.isEmpty() ? priceTree.firstKey() : -1;
	}

	public IOrderList getMinPriceOrderList() {
		return !priceTree.isEmpty() ? getOrderList(getMinPrice()) : null;
	}
	
	public IOrderList getOrderList(long price) {
		return priceMap.get(price);
	}
	
	public int getVolume() {
		return volume;
	}

	public void removeOrder(int orderId) {
		IOrder order = orderMap.get(orderId);
		if(order != null) {
			this.volume -= order.getQuantity();
			order.getOrderList().remove(order);
			if (order.getOrderList().size() == 0) {
				this.removePrice(order.getPrice());
			}
			this.orderMap.remove(orderId);
		}
	}

	public void removePrice(long price) {
		if(priceMap.containsKey(price)) {
			priceTree.remove(price);
			priceMap.remove(price);
		}
	}

	public void updateQuantity(int orderId, int newQuantity) {
		IOrder order = this.orderMap.get(orderId);
		int oldQuantity = order.getQuantity();
		OrderListLibrary.updateQuantity(order, newQuantity);
		this.volume += (order.getQuantity() - oldQuantity);
	}
	
	public int size() {
		return orderMap.size();
	}
}

