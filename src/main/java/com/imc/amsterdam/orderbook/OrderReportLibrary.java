package com.imc.amsterdam.orderbook;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class OrderReportLibrary 
{
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd, HH:mm:ss.SSS");
	
	private static final DecimalFormat df = new DecimalFormat("#.0000");

	public static void print(IOrderReport orderReport) {
		System.out.println("[Order Report] " + getOrder(orderReport.getOrder()));
		List<Trade> trades = orderReport.getTrades();
		if(trades != null) {
			for(Trade trade : trades)
				System.out.println(getTrade(trade));
		}
	}
	
	public static String getOrder(IOrder order) {
		return "OrderId:" + order.getOrderId() + ", Side:" + order.getSide() + ", Price:" + df.format(FixPoint.toDouble(order.getPrice())) + ", Quantity:" + order.getQuantity() + ", BrokerId:" + order.getBrokerId() + ", Timestamp " + sdf.format(order.getTimestamp());
	}
	
	public static String getTrade(Trade trade) {
		return "OrderId:" + trade.getOrderId() + ", Price:" + df.format(FixPoint.toDouble(trade.getPrice())) + ", Quantity:" + trade.getQuantity() + ", Buyer:" + trade.getBuyerId() + ", Seller:" + trade.getSellerId() + ", Timestamp " + sdf.format(trade.getTimestamp());
	}
}