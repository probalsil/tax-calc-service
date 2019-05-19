package com.sap.slh.tax.calculation.ruleEngine;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.postgresql.ds.PGSimpleDataSource;
import org.postgresql.util.PGobject;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
@Controller
public class RuleController {
	
DataSource ds = new DataSource();
	
	@RequestMapping(value = { "/v1/CalcRule" }, method = RequestMethod.POST)
	public ResponseEntity<String> insert(@RequestBody String request) throws SQLException, IOException, ParseException, JSONException {
        
		
		PGSimpleDataSource datasource = ds.getDataSource();
		Connection con = datasource.getConnection();
		try {
			Statement statement = con.createStatement();
			//statement.execute(String.format("CREATE SCHEMA IF NOT EXISTS \"%s\"", tenantId));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Statement st1 = con.createStatement();
		String sql = "CREATE TABLE IF not exists Rule("
				+ "RuleId INTEGER PRIMARY KEY, TaxEvent VARCHAR(255), Country VARCHAR(10), Rule JSON , Factor JSON)";
		st1.execute(sql);
		JSONArray jarr = new JSONArray(request);
		for(int i = 0 ;i < jarr.length() ; i++) {
	    try {
		JSONObject jobj = jarr.getJSONObject(i);
		int id  = (int) jobj.get("id");
		String taxEvent = jobj.getString("taxEvent");
		String rule = jobj.getJSONArray("rule").toString();
		String country = jobj.getString("country");
		
		String factor = null;
		if(jobj.has("factorRule"))			
		factor = jobj.getJSONArray("factorRule").toString();

		PGobject jsonObject = new PGobject();
		jsonObject.setType("json");
		jsonObject.setValue(rule);
		
		PGobject factorrule = new PGobject();
		factorrule.setType("json");
		factorrule.setValue(factor);


		PreparedStatement pstmt = con
				.prepareStatement("INSERT INTO Rule(RuleId,TaxEvent,Country,Rule,Factor) VALUES (?,?,?,?,?)");
		pstmt.setInt(1, id);
		pstmt.setString(2, taxEvent);
		pstmt.setString(3, country);
		pstmt.setObject(4, jsonObject);
		pstmt.setObject(5, factorrule);
		pstmt.executeUpdate();
		}catch(Exception e)		
		{
			con.close();
			return new ResponseEntity<String>(e.getMessage()+e.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}	    
		}
		con.close();
		return new ResponseEntity<String>("Successfully Inserted", HttpStatus.OK);
	}

	@RequestMapping(value = { "/v1/CalcRule" }, method = RequestMethod.GET)
	public void getData(HttpServletResponse response, HttpServletRequest request)
			throws SQLException, IOException, JSONException {
		PGSimpleDataSource datasource = ds.getDataSource();
		response.setHeader("Content-Type", "application/json");
		Connection con = datasource.getConnection();
		String RuleId = request.getParameter("RuleId");
		try {
			Statement statement = con.createStatement();
			//statement.execute(String.format("CREATE SCHEMA IF NOT EXISTS \"%s\"", tenantId));
		} catch (Exception e) {
			e.printStackTrace();
		}
		//con.setSchema(tenantId);
		List<String> output = new ArrayList<>();
		ResultSet resultset = null;
		if (RuleId != null ) {
			if(!RuleId.isEmpty()) {
			PreparedStatement statement = con.prepareStatement("select * from Rule where RuleId = ? ");
			statement.setInt(1,Integer.parseInt(RuleId));
			resultset = statement.executeQuery();
		} } else {
			PreparedStatement statement = con.prepareStatement("select * from Rule ");
			resultset = statement.executeQuery();
		}
	
		//JSONArray jsonArray = new JSONArray();
		List<String> str = new ArrayList();
		while (resultset.next()) {
			//String jsonArray = null;
			/*String json = null;
			int total_rows = resultset.getMetaData().getColumnCount();
			for (int i = 0; i < total_rows; i++) {

				String columnName = resultset.getMetaData().getColumnLabel(i + 1).toLowerCase();
				if (columnName.equals("rule")) {
					json = resultset.getString("rule");
					str.add(json);
				}
				
			}*/
			 int numColumns = resultset.getMetaData().getColumnCount();
			  JSONObject obj = new JSONObject();
			  for (int i= 0 ; i< numColumns; i++) {
			    String column_name = resultset.getMetaData().getColumnLabel(i + 1).toLowerCase();
			    obj.put(column_name, resultset.getObject(column_name));
			  }
			  
			str.add(obj.toString());;
			
		}
		con.close();
		PrintWriter printer = response.getWriter();
		printer.print(str.toString());

	}

	@RequestMapping(value = { "/v1/CalcRule" }, method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteData(HttpServletRequest request) throws SQLException, JSONException {
		PGSimpleDataSource datasource = ds.getDataSource();
		Connection con = datasource.getConnection();
		String RuleId = request.getParameter("RuleId");
		try {
			Statement statement = con.createStatement();
			//statement.execute(String.format("CREATE SCHEMA IF NOT EXISTS \"%s\"", tenantId));
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (RuleId != null ) {
			if( !RuleId.isEmpty()) {	
			PreparedStatement statement = con.prepareStatement("delete from Rule where RuleId = ? ");
			statement.setInt(1,Integer.parseInt(RuleId));
			statement.executeUpdate();
		} } else {
			Statement st1 = con.createStatement();
			String sql = "DROP TABLE IF exists Rule";
			st1.execute(sql);
		}
		con.close();
		return new ResponseEntity<String>("Successfully Deleted", HttpStatus.OK);
	}

}
