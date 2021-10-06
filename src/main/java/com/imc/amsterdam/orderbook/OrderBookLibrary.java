package com.imc.amsterdam.orderbook;

public class OrderBookLibrary
{
    public static long getBestAsk(IOrderBook orderBook) {
        return orderBook.getAsks().getMinPrice();
    }

    public static long getBestBid(IOrderBook orderBook) {
        return orderBook.getBids().getMaxPrice();
    }

    public static long getBidAskSpread(IOrderBook orderBook) {
        return getBestAsk(orderBook) - getBestBid(orderBook);
    }

    public static long getMidPoint(IOrderBook orderBook) {
        return getBestBid(orderBook) + ( getBidAskSpread(orderBook) >> 2 );
    }

    public static int getVolumeOnSide(IOrderBook orderBook, Side side) {
        switch(side) {
            case BUY :
                return orderBook.getBids().getVolume();
            case SELL:
                return orderBook.getAsks().getVolume();
        }
    	throw new RuntimeException("Unknown Side:" + side);
    }

    public static int getVolumeAtPrice(IOrderBook orderBook, Side side, long price) {
        switch(side) {
            case BUY :
                if(orderBook.getBids().containsPrice(price))
                    return orderBook.getBids().getOrderList(price).getVolume();
            case SELL:
                if(orderBook.getAsks().containsPrice(price))
                    return orderBook.getAsks().getOrderList(price).getVolume();
        }
    	throw new RuntimeException("Unknown Side:" + side);
    }
}
