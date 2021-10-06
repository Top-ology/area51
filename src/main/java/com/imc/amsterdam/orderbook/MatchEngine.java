package com.imc.amsterdam.orderbook;

import java.util.ArrayList;
import java.util.List;

public class MatchEngine implements IMatchEngine
{
    private final IOrderBook orderBook;

    private int nextOrderId = 0;

    public MatchEngine(IOrderBook orderBook) {
        this.orderBook = orderBook;
    }

    @Override
    public IOrderReport submitOrder(IOrder order) {
        OrderValidator.isValid(order);
        return OrderLibrary.isLimitOrder(order) ? processLimitOrder(order) : processMarketOrder(order);
    }

    private IOrderReport processLimitOrder(IOrder order) {
        List<Trade> trades = new ArrayList<>();
        int quantity = order.getQuantity();
        switch(order.getSide()) {
            case BUY :
            	IPriceLevels asks = orderBook.getAsks();
                while(quantity > 0 && asks.size() > 0 && order.getPrice() >= asks.getMinPrice()) {
                    IOrderList bestAsks =  asks.getMinPriceOrderList();
                    quantity = processOrderList(order, quantity, bestAsks, trades);
                }
                if(quantity > 0) {
                    order.setOrderId(this.nextOrderId);
                    order.setQuantity(quantity);
                    orderBook.getBids().addOrder(order);
                    this.nextOrderId++;
                }
                break;
            case SELL:
            	IPriceLevels bids = orderBook.getBids();
                while(quantity > 0 && bids.size() > 0 && order.getPrice() <= bids.getMaxPrice()) {
                    IOrderList bestBids = bids.getMaxPriceOrderList();
                    quantity = processOrderList(order, quantity, bestBids, trades);
                }
                if(quantity > 0) {
                    order.setOrderId(this.nextOrderId);
                    order.setQuantity(quantity);
                    orderBook.getAsks().addOrder(order);
                    this.nextOrderId++;
                }
        }
        return new OrderReport(order, trades);
    }

    private IOrderReport processMarketOrder(IOrder order) {
        List<Trade> trades = new ArrayList<>();
        int quantity = order.getQuantity();
        switch(order.getSide()) {
            case BUY :
            	IPriceLevels asks = orderBook.getAsks();
                while(quantity > 0 && asks.size() > 0) {
                    IOrderList bestAsks = asks.getMinPriceOrderList();
                    quantity = processOrderList(order, quantity, bestAsks, trades);
                }
                break;
            case SELL :
            	IPriceLevels bids = orderBook.getBids();
                while(quantity > 0 && bids.size() > 0) {
                    IOrderList bestBids = bids.getMaxPriceOrderList();
                    quantity = processOrderList(order, quantity, bestBids, trades);
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown Side:" + order.getSide());
        }
        return new OrderReport(order, trades);
    }

    private int processOrderList(IOrder order, int leavesQty, IOrderList bestOrders, List<Trade> trades) {
        Side side = order.getSide(); 
        while (bestOrders.size() > 0 && leavesQty > 0) {
            int tradeQty = 0;
            IOrder head = bestOrders.getHead();
            if (leavesQty < head.getQuantity()) {
                tradeQty = leavesQty;
                IPriceLevels priceLevels = ( side == Side.SELL ) ? this.orderBook.getBids() : this.orderBook.getAsks();
                priceLevels.updateQuantity(head.getOrderId(), head.getQuantity() - leavesQty);
                trades.add(getTrade(side, order, head, tradeQty, Clock.now()));
                leavesQty = 0;
            } else {
                tradeQty = head.getQuantity();
                IPriceLevels priceLevels = ( side == Side.SELL ) ? this.orderBook.getBids() : this.orderBook.getAsks();
                priceLevels.removeOrder(head.getOrderId());
                trades.add(getTrade(side, order, head, tradeQty, Clock.now()));
                leavesQty -= tradeQty;
            }
        }
        return leavesQty;
    }
    
    private Trade getTrade(Side side, IOrder order, IOrder head, int quantity, long timestamp) {
    	switch(side) {
    	case BUY :
    		return new Trade(order.getOrderId(), head.getPrice(), quantity, order.getBrokerId(), head.getBrokerId(), timestamp);
    	case SELL: 
    		return new Trade(order.getOrderId(), head.getPrice(), quantity, head.getBrokerId(), order.getBrokerId(), timestamp);
    	}
    	throw new RuntimeException("Unknown Side:" + side);
    }

}
