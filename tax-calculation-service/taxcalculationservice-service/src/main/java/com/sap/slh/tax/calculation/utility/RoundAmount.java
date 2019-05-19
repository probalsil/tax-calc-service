package com.sap.slh.tax.calculation.utility;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class RoundAmount {
	
	public static double roundAmount(int places, double value)
	{
		BigDecimal bd = new BigDecimal(Double.toString(value));
	    bd = bd.setScale(places, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	}

}