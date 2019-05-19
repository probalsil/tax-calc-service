package com.sap.slh.tax.calculation.ruleEngine;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.jexl3.JexlBuilder;
import org.apache.commons.jexl3.JexlContext;
import org.apache.commons.jexl3.JexlEngine;
import org.apache.commons.jexl3.JexlExpression;
import org.apache.commons.jexl3.MapContext;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.sap.slh.tax.calculation.dto.TaxCalculationOutputDto;
import com.sap.slh.tax.calculation.exception.InvalidParameterException;
import com.sap.slh.tax.calculation.model.api.DebugInfo;
import com.sap.slh.tax.calculation.model.common.AdditionalValue;
import com.sap.slh.tax.calculation.model.common.TaxCalculationRequest;
import com.sap.slh.tax.calculation.model.common.TaxCalculationResponse;
import com.sap.slh.tax.calculation.model.common.TaxLine;
import com.sap.slh.tax.calculation.model.common.TaxValue;
import com.sap.slh.tax.calculation.model.uri.PathParamConstant;
import com.sap.slh.tax.calculation.utility.JsonUtil;
import com.sap.slh.tax.calculation.utility.RoundAmount;

@Component("TaxCalculationResponseBuilder")
public class TaxCalculationResponseBuilder {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(TaxCalculationResponseBuilder.class);
	HashMap<String,String> calculatedValues = new HashMap<String,String>();
	static HashMap<String,String> taxTypeDescriptionMap = new HashMap<>();
	private static final JexlEngine jexl = new JexlBuilder().cache(512).strict(true).silent(false).create();
	
	@PostConstruct
	public void taxTypeDescriptionMapping()
	{
		taxTypeDescriptionMap.put("1","GST");
		taxTypeDescriptionMap.put("2", "HST");
		taxTypeDescriptionMap.put("3", "PST");
		taxTypeDescriptionMap.put("4", "QST");
		taxTypeDescriptionMap.put("5", "RITC");
		
		
	}
	
	
	public List<TaxCalculationResponse>  buildResponse(List<TaxCalculationRequest> taxCalculationRequestList, List<TaxCalculationOutputDto> outputList)
	{   
		List<TaxCalculationResponse> responseList = new ArrayList<>();
		if( taxCalculationRequestList != null && outputList != null )
		{   
			taxCalculationRequestList.stream().forEach( taxCalculationRequest -> {
				
				List<TaxValue> taxValues = new ArrayList<>();
				List<TaxLine> taxLines = taxCalculationRequest.getTaxLines();
				taxLines.sort(Comparator.comparing(TaxLine::getTaxType));
				HashMap<String,String> map = buildTaxTypeRateMap(taxLines);
				
				
				taxLines.forEach( taxline ->{			
					try {
					LOGGER.debug(" The taxLine sorted is {}",JsonUtil.toJsonString(taxline));
					JSONObject requestJson = new JSONObject(JsonUtil.toJsonString(taxCalculationRequest));
					JSONObject taxlineJson = new JSONObject(JsonUtil.toJsonString(taxline));
					JSONArray ruleResponse = null;	
					JSONArray factorResponse = null;
					for(TaxCalculationOutputDto outputDto :outputList )
					{
						if(outputDto.getItemId().equalsIgnoreCase(taxCalculationRequest.getItemId()) && outputDto.getTaxLineId().equalsIgnoreCase(taxline.getId()))
				        ruleResponse = outputDto.getOutputs();
						factorResponse = outputDto.getFactor();
						LOGGER.info(" Rule response {} for request {} taxline {}", ruleResponse, requestJson, taxlineJson );
					}
					
					if(factorResponse != null)
					calculateFactor(map,factorResponse);
				
					HashMap<String,String> displayList = calculateTaxPerTaxLevel(requestJson,taxlineJson,ruleResponse);
					TaxValue taxValue= buildTaxValue(taxCalculationRequest, taxline, displayList);
					taxValues.add(taxValue);
					displayList.clear();
				} catch (JSONException e) {
					e.printStackTrace();
				}
				});
				
				
				TaxCalculationResponse response = buildTaxCalculationResponse(taxCalculationRequest, taxValues);
				responseList.add(response);
				LOGGER.info("The Calculated values are cleared ");
				calculatedValues.clear();	
				
				LOGGER.info("The size of calculated values {}",calculatedValues.size());
				
			});
		}
		return responseList;
		
	}
	
        private void calculateFactor(HashMap<String, String> map, JSONArray factorResponse) throws JSONException {
		 
        	for (int x = 0; x < factorResponse.length(); x++) {
				JSONObject output = factorResponse.getJSONObject(x);	
				String calculatedValue = output.getString("output");				
				if(calculatedValues.containsKey(calculatedValue))
					break;
				String formula = output.getString("formula");
				LOGGER.debug("The formula is {}",formula);
	            JSONArray parameters = output.getJSONArray("parameters");
				JexlExpression e = jexl.createExpression(formula);
				JexlContext context = new MapContext();
                LOGGER.debug("The length of parameters {}",parameters.length());
				for (int y = 0; y < parameters.length(); y++) {
					String parameter = parameters.getString(y);
				LOGGER.debug("the  {}",map.get(parameter));
					context.set(parameter, Double.parseDouble(map.get(parameter)));
				}
				
				Double result = (Double) e.evaluate(context);
				calculatedValues.put(calculatedValue, result.toString());
				
				LOGGER.debug("The calculated {} is {}",calculatedValue,result.toString());
			
				}
	
			}
		
	    


		private HashMap<String,String> buildTaxTypeRateMap(List<TaxLine> taxLines) {
        	
        	HashMap<String,String> taxTypeRateMap = new HashMap<>();
        	taxLines.stream().forEach( taxLine -> {          		
        		taxTypeRateMap.put(taxTypeDescriptionMap.get(taxLine.getTaxType()),taxLine.getTaxRate());
        	LOGGER.debug(" Tax type key {}",taxTypeDescriptionMap.get(taxLine.getTaxType()));
        	LOGGER.debug(" Tax Rate value {} ",taxLine.getTaxRate());
        	LOGGER.debug(" Tax Type Description Map key {}, Value {}",taxTypeDescriptionMap.get(taxLine.getTaxType()),taxLine.getTaxRate());
        	
        	});
		    return taxTypeRateMap;
		
	    }

        private HashMap<String,String> calculateTaxPerTaxLevel(JSONObject requestJson, JSONObject taxline, JSONArray ruleResponse) throws JSONException {
		
		HashMap<String,String> displayValues = new HashMap<String,String>();
		if(ruleResponse!=null ) 
			for (int x = 0; x < ruleResponse.length(); x++) {
				JSONObject output = ruleResponse.getJSONObject(x);			
				String calculatedValue = output.getString("output");
				String formula = output.getString("formula");
	            JSONArray parameters = output.getJSONArray("parameters");
				JexlExpression e = jexl.createExpression(formula);
				JexlContext context = new MapContext();
				String value = null;

				for (int y = 0; y < parameters.length(); y++) {
					String parameter = parameters.getString(y);
					value = calculatedValues.get(parameter);
					if (requestJson.has(parameter) && value == null)
						value = requestJson.getString(parameter);
					else if (taxline.has(parameter) && value == null)
						value = taxline.getString(parameter);
					else if(value == null) {
					LOGGER.error("Could not find value for parameter {}",parameter);
					throw new InvalidParameterException(new DebugInfo.DebugInfoBuilder()
							.put(PathParamConstant.PARAMETER, parameter).build());
					}
					context.set(parameter, Double.parseDouble(value));
				}
				Double result = (Double) e.evaluate(context);
				result = RoundAmount.roundAmount(2, result);
				calculatedValues.put(calculatedValue, result.toString());
				String displayName = output.getString("displayName");
				
				LOGGER.info("Calculated amount {} for parameter {}",result,displayName);
				if(displayName != null && !StringUtils.isEmpty(displayName)) {
				displayValues.put(displayName,result.toString());
				LOGGER.info("Calculated Values {}",displayValues.size());
				}
	
			}
		return displayValues;
	}
        public TaxValue buildTaxValue(TaxCalculationRequest calculationRequest , TaxLine taxline , HashMap<String,String> displayList) throws JSONException
    	{   
    		TaxValue taxValue = new TaxValue();
    		List<AdditionalValue> additionalValueList = new ArrayList<>();
    		
    		taxValue.setId(taxline.getId());
    		for(String key :displayList.keySet()) {
    			if(key.equals("baseAmount"))
    				taxValue.setBaseAmount(displayList.get(key));
    			else if(key.equalsIgnoreCase("taxAmount"))
    				taxValue.setTaxAmount(displayList.get(key));
    			else if(key.equals("deductibleTaxAmount"))
    				taxValue.setDeductibleTaxAmount(displayList.get(key));
    			else if(key.equals("nonDeductibleTaxAmount"))
    				taxValue.setNonDeductibleTaxAmount(displayList.get(key));
    			else {
    			AdditionalValue addVal = new AdditionalValue();
    			addVal.setKey(key);
    			addVal.setValue(displayList.get(key));
    			additionalValueList.add(addVal);
    			}
    		}
    		if(additionalValueList != null && !CollectionUtils.isEmpty(additionalValueList))
    		taxValue.setAdditionalValues(additionalValueList);
    		return taxValue;
    	}
    	
    	public TaxCalculationResponse buildTaxCalculationResponse(TaxCalculationRequest calculationRequest, List<TaxValue> taxValue) {
    		TaxCalculationResponse response = new TaxCalculationResponse();
    		response.setItemId(calculationRequest.getItemId());
    		response.setParentId(calculationRequest.getParentId());
    		response.setTaxValues(taxValue);
    		return response;
    	}
    	
}
