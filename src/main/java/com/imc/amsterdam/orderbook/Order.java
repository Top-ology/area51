package com.imc.amsterdam.orderbook;

public class Order implements IOrder
{
	private int orderId;
	private OrderType orderType;
	private Side side;
	private long price;
	private int quantity;
	private int brokerId;
	private long timestamp;
	
	private IOrder next;
	private IOrder prev;
	private IOrderList orderList;
	
	public Order(int brokerId, OrderType orderType, Side side, long price, int quantity, long timestamp) {
		this.brokerId = brokerId; this.orderType = orderType;this.side = side; this.price = price; this.quantity = quantity; this.timestamp = timestamp;
		
		this.next = null; this.prev = null; this.orderList = null;
	}
	
	public int getOrderId() { 
		return orderId; 
	}
	
	public OrderType getOrderType() { 
		return orderType;
	}
	
	public long getPrice() { 
		return price; 
	}
	
	public int getQuantity() { 
		return quantity;	
	}
	
	public Side getSide() {	
		return side; 
	}
	
	public int getBrokerId() { 
		return brokerId; 
	}
	
	public long getTimestamp() { 
		return timestamp; 
	}
	
	public void setOrderId(int orderId) { 
		this.orderId = orderId; 
	}
	
	public void setOrderType(OrderType orderType) { 
		this.orderType = orderType; 
	}
	
	public void setSide(Side side) { 
		this.side = side; 
	}
	
	public void setPrice(long price) { 
		this.price = price; 
	}
	
	public void setQuantity(int quantity) {	
		this.quantity = quantity; 
	}
	
	public void setBrokerId(int brokerId) { 
		this.brokerId = brokerId; 
	}
	
	public void setTimestamp(long timestamp) { 
		this.timestamp = timestamp; 
	}
	
	// next, previous, container references	
	
	public IOrder getNext() {
		return next; 
	}
	
	public IOrder getPrev() { 
		return prev;	
	}
	
	public IOrderList getOrderList() { 
		return orderList; 
	}

	public void setNext(IOrder next) { 
		this.next = next;
	}
	
	public void setPrev(IOrder prev) { 
		this.prev = prev; 
	}
	
	public void setOrderList(IOrderList orderList) { 
		this.orderList = orderList; 
	}
}
