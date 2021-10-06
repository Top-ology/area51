package com.imc.amsterdam.orderbook;

public class OrderListLibrary 
{	
    public static void updateQuantity(IOrder order, int newQuantity) {
        IOrderList orderList = order.getOrderList();
        if(newQuantity > order.getQuantity()) {
            if(orderList.getTail() != order) {
            	orderList.remove(order);
            	orderList.add(order);
            }
        }
        orderList.setVolume(orderList.getVolume()-order.getQuantity()+newQuantity);
        order.setQuantity(newQuantity);
    }
}
