package com.imc.amsterdam.orderbook;

import java.util.Iterator;

public class OrderList implements IOrderList
{
	private IOrder head = null;
	private IOrder tail = null;
	private int size = 0;
	private int volume = 0;

	public void add(IOrder order) {
		if (size == 0) addHead(order); else addTail(order);
		size += 1;
		volume += order.getQuantity();
	}
	
	private void addHead(IOrder order) {
		order.setNext(null);
		order.setPrev(null);
		head = order;
		tail = order;
	}
	
	private void addTail(IOrder order) {
		order.setPrev(tail);
		order.setNext(null);
		tail.setNext(order);
		tail = order;
	}
	
	public void clear() {
		this.head = null; this.head = null; this.volume = 0; this.size = 0;
	}
	
	public IOrder getHead() {
		return head;
	}

	public IOrder getTail() {
		return tail;
	}

	public int getVolume() {
		return volume;
	}

	public Iterator<IOrder> iterator() {
		return new OrderListIterator(head);
	}

	public void remove(IOrder order) {
		if(size > 0) {
			if(size == 1) {
				clear();
			} else {
				IOrder next = order.getNext(); IOrder prev = order.getPrev();
				
				if(next != null && prev != null) {
					next.setPrev(prev); prev.setNext(next);
				} else
				if(next != null && prev == null) {
					this.head = next; next.setPrev(null); 
				} else
				if(next == null && prev != null) {
					this.tail = prev; prev.setNext(null); 
				}
				
				this.volume -= order.getQuantity();
				this.size -= 1;
			}
		}
	}
	
	public void setVolume(int volume) {
		this.volume = volume;
	}
	
	public int size() {
		return size;
	}
}
