package com.sap.slh.tax.calculation.ruleEngine;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.postgresql.ds.PGSimpleDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Component("GetRulesFromDB")
public class GetRulesFromDB {
	
	private Logger log = LoggerFactory.getLogger(GetRulesFromDB.class);

	public String getDataFromRule(String taxevent,String country) throws JSONException, SQLException {
		
		DataSource datasource = new DataSource();
		PGSimpleDataSource ds = datasource.getDataSource();
		Connection con = ds.getConnection();
		ResultSet resultset = null;

		PreparedStatement statement = con.prepareStatement("select * from Rule where TaxEvent = ? and Country = ? ");
		statement.setString(1, taxevent);
		statement.setString(2,country);
		resultset = statement.executeQuery();
		String ruleResult = null;
		while (resultset.next()) {
		int total_rows = resultset.getMetaData().getColumnCount();
		log.info("total_rows {}",total_rows);
		
		for (int i = 0; i < total_rows; i++) {

			String columnName = resultset.getMetaData().getColumnLabel(i + 1).toLowerCase();
			if (columnName.equals("rule")) {
				ruleResult = resultset.getString("rule");
			
			}		
		}
		}
		log.info("output of database call for rule {}",ruleResult);

		con.close();
		return ruleResult;
	}
	
     public String getFactorRule(String taxevent,String country) throws JSONException, SQLException {
		
		DataSource datasource = new DataSource();
		PGSimpleDataSource ds = datasource.getDataSource();
		Connection con = ds.getConnection();
		ResultSet resultset = null;

		PreparedStatement statement = con.prepareStatement("select * from Rule where TaxEvent = ? and Country = ?");
		statement.setString(1,taxevent);
		statement.setString(2,country);
		resultset = statement.executeQuery();
		String ruleResult = null;
		while (resultset.next()) {
		int total_rows = resultset.getMetaData().getColumnCount();
		log.info("total_rows {}",total_rows);
		
		for (int i = 0; i < total_rows; i++) {

			String columnName = resultset.getMetaData().getColumnLabel(i + 1).toLowerCase();
			if (columnName.equals("factor")) {
				ruleResult = resultset.getString("factor");
			
			}		
		}
		}
		log.info("output of database call for factor Rule {}",ruleResult);

		con.close();
		return ruleResult;
	}
	
	
}
