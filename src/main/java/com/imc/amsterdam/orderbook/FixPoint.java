package com.imc.amsterdam.orderbook;

public class FixPoint
{
    public static int DECIMAL_PLACES = 4;

    public static long toLong(double d) {
        return (long)(d*Math.pow(10, DECIMAL_PLACES));
    }
    
    public static double toDouble(long l) {
    	return (double)l / (double) Math.pow(10, DECIMAL_PLACES);
    }
}
