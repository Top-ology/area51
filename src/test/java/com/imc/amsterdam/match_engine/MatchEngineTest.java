package com.imc.amsterdam.match_engine;

import com.imc.amsterdam.orderbook.Clock;
import com.imc.amsterdam.orderbook.FixPoint;
import com.imc.amsterdam.orderbook.IMatchEngine;
import com.imc.amsterdam.orderbook.IOrder;
import com.imc.amsterdam.orderbook.IOrderBook;
import com.imc.amsterdam.orderbook.IOrderReport;
import com.imc.amsterdam.orderbook.MatchEngine;
import com.imc.amsterdam.orderbook.OrderBook;
import com.imc.amsterdam.orderbook.OrderFactory;
import com.imc.amsterdam.orderbook.OrderReportLibrary;
import com.imc.amsterdam.orderbook.Side;

import org.junit.Assert;
import org.junit.Test;

public class MatchEngineTest 
{	
	private final IOrderBook orderBook = new OrderBook();
	
	private final IMatchEngine matchEngine = new MatchEngine(orderBook);
	
	private final OrderFactory orderFactory = new OrderFactory();
	
	private final int SELL_BROKER = 1;
	
	private final int  BUY_BROKER = 2;
	
	@Test
	public void _1BuyOrder() {
		IOrder order = orderFactory.getLimitOrder(BUY_BROKER, Side.BUY, FixPoint.toLong(10.00), 1000, Clock.now());
		IOrderReport orderReport = matchEngine.submitOrder(order);
		Assert.assertEquals(orderBook.getBids().size(), 1);
		Assert.assertEquals(orderBook.getBids().getVolume(), 1000);
		Assert.assertEquals(orderBook.getAsks().size(), 0);
		Assert.assertEquals(orderBook.getAsks().getVolume(), 0);
		Assert.assertTrue(orderReport.isEmpty());
	}
	
	@Test
	public void _1SellOrder() {
		IOrder order = orderFactory.getLimitOrder(SELL_BROKER, Side.SELL, FixPoint.toLong(10.00), 1000, Clock.now());
		IOrderReport orderReport = matchEngine.submitOrder(order);
		Assert.assertEquals(orderBook.getBids().size(), 0);
		Assert.assertEquals(orderBook.getBids().getVolume(), 0);
		Assert.assertEquals(orderBook.getAsks().size(), 1);
		Assert.assertEquals(orderBook.getAsks().getVolume(), 1000);
		Assert.assertTrue(orderReport.isEmpty());
	}
	
	@Test
	public void _10BuyOrders() {
		for(int price = 10; price > 0; price--) {
			IOrder buyOrder = orderFactory.getLimitOrder(BUY_BROKER, Side.BUY, FixPoint.toLong(price), 100, Clock.now());
			matchEngine.submitOrder(buyOrder);
		}
		Assert.assertEquals(orderBook.getBids().size(), 10);
		Assert.assertEquals(orderBook.getBids().getVolume(), 1000);
		Assert.assertEquals(orderBook.getAsks().size(), 0);
		Assert.assertEquals(orderBook.getAsks().getVolume(), 0);
	}
	
	@Test
	public void _10SellOrders() {
		for(int price = 10; price > 0; price--) {
			IOrder sellOrder = orderFactory.getLimitOrder(SELL_BROKER, Side.SELL, FixPoint.toLong(price), 100, Clock.now());
			matchEngine.submitOrder(sellOrder);
		}
		Assert.assertEquals(orderBook.getBids().size(), 0);
		Assert.assertEquals(orderBook.getBids().getVolume(), 0);
		Assert.assertEquals(orderBook.getAsks().size(), 10);
		Assert.assertEquals(orderBook.getAsks().getVolume(), 1000);
	}
	
	@Test
	public void _1LimitBuyCross1LimitSellSamePrice() {
		IOrder sellOrder = orderFactory.getLimitOrder(SELL_BROKER, Side.SELL, FixPoint.toLong(10.00), 1000, Clock.now());
		matchEngine.submitOrder(sellOrder);
		Assert.assertEquals(orderBook.getBids().size(), 0);
		Assert.assertEquals(orderBook.getAsks().size(), 1);
		Assert.assertEquals(orderBook.getAsks().getVolume(), 1000);
		
		IOrder buyOrder = orderFactory.getLimitOrder(BUY_BROKER, Side.BUY, FixPoint.toLong(10.00), 1000, Clock.now());
		IOrderReport orderReport = matchEngine.submitOrder(buyOrder);
		Assert.assertEquals(orderBook.getBids().size(), 0);
		Assert.assertEquals(orderBook.getBids().getVolume(), 0);
		Assert.assertEquals(orderBook.getAsks().size(), 0);
		Assert.assertEquals(orderBook.getAsks().getVolume(), 0);
		
		Assert.assertEquals(orderReport.size(), 1);
		OrderReportLibrary.print(orderReport);
	}
	
	@Test
	public void _1LimitBuyCross2LimitSellSamePrice() {
		for(int index = 0; index < 2; index++) {
			IOrder sellOrder = orderFactory.getLimitOrder(SELL_BROKER, Side.SELL, FixPoint.toLong(10.00), 500, Clock.now());
			matchEngine.submitOrder(sellOrder);
		}
		Assert.assertEquals(orderBook.getBids().size(), 0);
		Assert.assertEquals(orderBook.getBids().getVolume(), 0);
		Assert.assertEquals(orderBook.getAsks().size(), 2);
		Assert.assertEquals(orderBook.getAsks().getVolume(), 1000);
		
		IOrder buyOrder  = orderFactory.getLimitOrder(BUY_BROKER, Side.BUY, FixPoint.toLong(10.00), 1000, Clock.now());
		IOrderReport orderReport = matchEngine.submitOrder(buyOrder);
		Assert.assertEquals(orderBook.getBids().size(), 0);
		Assert.assertEquals(orderBook.getBids().getVolume(), 0);
		Assert.assertEquals(orderBook.getAsks().size(), 0);
		Assert.assertEquals(orderBook.getAsks().getVolume(), 0);
		
		Assert.assertEquals(orderReport.size(), 2);
		OrderReportLibrary.print(orderReport);
	}
	
	@Test
	public void _1LimitSellCross2LimitBuysSamePrice() {
		for(int index = 0; index < 2; index++) {
			IOrder sellOrder = orderFactory.getLimitOrder(BUY_BROKER, Side.BUY, FixPoint.toLong(10.00), 500, Clock.now());
			matchEngine.submitOrder(sellOrder);
		}
		Assert.assertEquals(orderBook.getBids().size(), 2);
		Assert.assertEquals(orderBook.getBids().getVolume(), 1000);
		Assert.assertEquals(orderBook.getAsks().size(), 0);
		Assert.assertEquals(orderBook.getAsks().getVolume(), 0);
		
		IOrder sellOrder  = orderFactory.getLimitOrder(SELL_BROKER, Side.SELL, FixPoint.toLong(10.00), 1000, Clock.now());
		IOrderReport orderReport = matchEngine.submitOrder(sellOrder);
		Assert.assertEquals(orderBook.getBids().size(), 0);
		Assert.assertEquals(orderBook.getBids().getVolume(), 0);
		Assert.assertEquals(orderBook.getAsks().size(), 0);
		Assert.assertEquals(orderBook.getAsks().getVolume(), 0);
		
		Assert.assertEquals(orderReport.size(), 2);
		OrderReportLibrary.print(orderReport);
	}
	
	@Test
	public void _1LimitBuyCross10LimitSellDifferentPrices() {
		for(int price = 10; price > 0; price--) {
			IOrder sellOrder = orderFactory.getLimitOrder(SELL_BROKER, Side.SELL, FixPoint.toLong(price), 100, Clock.now());
			matchEngine.submitOrder(sellOrder);
		}
		Assert.assertEquals(orderBook.getBids().size(), 0);
		Assert.assertEquals(orderBook.getBids().getVolume(), 0);
		Assert.assertEquals(orderBook.getAsks().size(), 10);
		Assert.assertEquals(orderBook.getAsks().getVolume(), 1000);
		
		IOrder buyOrder = orderFactory.getLimitOrder(BUY_BROKER, Side.BUY, FixPoint.toLong(10.00), 1000, Clock.now());
		IOrderReport orderReport = matchEngine.submitOrder(buyOrder);
		Assert.assertEquals(orderBook.getBids().size(), 0);
		Assert.assertEquals(orderBook.getBids().getVolume(), 0);
		Assert.assertEquals(orderBook.getAsks().size(), 0);
		Assert.assertEquals(orderBook.getAsks().getVolume(), 0);
		
		Assert.assertEquals(orderReport.size(), 10);
		OrderReportLibrary.print(orderReport);
	}
	
	@Test
	public void _1LimitSellCross10LimitBuyDifferentPrices() {
		for(int price = 10; price > 0; price--) {
			IOrder buyOrder = orderFactory.getLimitOrder(BUY_BROKER, Side.BUY, FixPoint.toLong(price), 100, Clock.now());
			matchEngine.submitOrder(buyOrder);
		}
		Assert.assertEquals(orderBook.getBids().size(), 10);
		Assert.assertEquals(orderBook.getBids().getVolume(), 1000);
		Assert.assertEquals(orderBook.getAsks().size(), 0);
		Assert.assertEquals(orderBook.getAsks().getVolume(), 0);
		
		IOrder sellOrder  = orderFactory.getLimitOrder(SELL_BROKER, Side.SELL, FixPoint.toLong(1.00), 1000, Clock.now());
		IOrderReport orderReport = matchEngine.submitOrder(sellOrder);
		Assert.assertEquals(orderBook.getBids().size(), 0);
		Assert.assertEquals(orderBook.getBids().getVolume(), 0);
		Assert.assertEquals(orderBook.getAsks().size(), 0);
		Assert.assertEquals(orderBook.getAsks().getVolume(), 0);
		
		Assert.assertEquals(orderReport.size(), 10);
		OrderReportLibrary.print(orderReport);
	}
	
	@Test
	public void _1MarketBuyCross10LimitSellDifferentPrices() {
		for(int price = 10; price > 0; price--) {
			IOrder sellOrder = orderFactory.getLimitOrder(SELL_BROKER, Side.SELL, FixPoint.toLong(price), 100, Clock.now());
			matchEngine.submitOrder(sellOrder);
		}
		Assert.assertEquals(orderBook.getBids().size(), 0);
		Assert.assertEquals(orderBook.getBids().getVolume(), 0);
		Assert.assertEquals(orderBook.getAsks().size(), 10);
		Assert.assertEquals(orderBook.getAsks().getVolume(), 1000);
		
		IOrder buyOrder = orderFactory.getMarketOrder(BUY_BROKER, Side.BUY, 1000, Clock.now());
		IOrderReport orderReport = matchEngine.submitOrder(buyOrder);
		Assert.assertEquals(orderBook.getBids().size(), 0);
		Assert.assertEquals(orderBook.getBids().getVolume(), 0);
		Assert.assertEquals(orderBook.getAsks().size(), 0);
		Assert.assertEquals(orderBook.getAsks().getVolume(), 0);
		
		Assert.assertEquals(orderReport.size(), 10);
		OrderReportLibrary.print(orderReport);
	}
	
	@Test
	public void _1MarketSellCross10LimitBuyDifferentPrices() {
		for(int price = 10; price > 0; price--) {
			IOrder buyOrder = orderFactory.getLimitOrder(BUY_BROKER, Side.BUY, FixPoint.toLong(price), 100, Clock.now());
			matchEngine.submitOrder(buyOrder);
		}
		Assert.assertEquals(orderBook.getBids().size(), 10);
		Assert.assertEquals(orderBook.getBids().getVolume(), 1000);
		Assert.assertEquals(orderBook.getAsks().size(), 0);
		Assert.assertEquals(orderBook.getAsks().getVolume(), 0);
		
		IOrder sellOrder = orderFactory.getMarketOrder(SELL_BROKER, Side.SELL, 1000, Clock.now());
		IOrderReport orderReport = matchEngine.submitOrder(sellOrder);
		Assert.assertEquals(orderBook.getBids().size(), 0);
		Assert.assertEquals(orderBook.getBids().getVolume(), 0);
		Assert.assertEquals(orderBook.getAsks().size(), 0);
		Assert.assertEquals(orderBook.getAsks().getVolume(), 0);
		
		Assert.assertEquals(orderReport.size(), 10);
		OrderReportLibrary.print(orderReport);
	}
}
