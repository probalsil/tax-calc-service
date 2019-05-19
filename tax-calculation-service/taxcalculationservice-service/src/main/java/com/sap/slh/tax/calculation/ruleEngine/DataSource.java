package com.sap.slh.tax.calculation.ruleEngine;

import org.json.JSONException;
import org.postgresql.ds.PGSimpleDataSource;


public class DataSource {
	
	public PGSimpleDataSource getDataSource() throws JSONException
	{   
		
		
		PGSimpleDataSource ds = new PGSimpleDataSource();
        ds.setServerName("10.11.241.104");
        ds.setDatabaseName("F-BDf7BzPlzlw_KM");
        ds.setUser("r65VHd94u5SYNgmZ");
        ds.setPassword("Gyaa_ersbR4bohsm");
        ds.setPortNumber(56841);
        return ds;
	}

}
