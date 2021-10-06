package com.imc.amsterdam.orderbook;

public class OrderValidator
{
    static boolean isValid(IOrder order) {
    	if(order.getPrice() <= 0 && order.getOrderType() != OrderType.MARKET)
    		throw new IllegalArgumentException("Invalid Price for Non-Market Order");
        if(order.getQuantity() <= 0)
            throw new IllegalArgumentException("Illegal Order Quantity:" + order.getQuantity());
        return true;
    }
}
