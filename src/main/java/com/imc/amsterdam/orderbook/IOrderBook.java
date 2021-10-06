package com.imc.amsterdam.orderbook;

public interface IOrderBook {
    void clear();
    void cancelOrder(Side side, int orderId);
    IPriceLevels getAsks();
    IPriceLevels getBids();
    void replaceOrder(int orderId, IOrder newOrder);
}
