package com.sap.slh.tax.calculation.dto;

import java.io.Serializable;

import org.json.JSONArray;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TaxCalculationOutputDto implements Serializable {
	
	private static final long serialVersionUID = 7805778924614357945L;
	
	private String itemId;
	
	private String taxLineId;
    
    private JSONArray outputs;
    
    private JSONArray factor;

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getTaxLineId() {
		return taxLineId;
	}

	public void setTaxLineId(String taxLineId) {
		this.taxLineId = taxLineId;
	}

	public JSONArray getOutputs() {
		return outputs;
	}

	public void setOutputs(JSONArray outputs) {
		this.outputs = outputs;
	}

	public JSONArray getFactor() {
		return factor;
	}

	public void setFactor(JSONArray factor) {
		this.factor = factor;
	}
   
}
