package com.imc.amsterdam.orderbook;

public interface IMatchEngine {
    IOrderReport submitOrder(IOrder order);
}
