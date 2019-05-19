package com.sap.slh.tax.calculation.ruleEngine;

import java.sql.SQLException;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.sap.slh.tax.calculation.RuleEngineTaxCalculationService;
import com.sap.slh.tax.calculation.model.common.TaxCalculationRequest;
import com.sap.slh.tax.calculation.model.common.TaxLine;
import com.sap.slh.tax.calculation.utility.JsonUtil;

@Component("RuleEvaluation")
public class RuleEvaluation {
     
	@Autowired 
	private GetRulesFromDB dbRead;
	
	private static final JexlEngine jexl = new JexlBuilder().cache(512).strict(true).silent(false).create();
	private static final Logger LOGGER = LoggerFactory.getLogger(RuleEvaluation.class);
	public JSONArray EvaluateRule(TaxCalculationRequest calculationRequest, JSONObject taxLine)
			throws JSONException, SQLException {
		
		String ruleResponse = dbRead.getDataFromRule(calculationRequest.getTaxEvent(),calculationRequest.getTaxCountry());
		JSONObject request = new JSONObject(JsonUtil.toJsonString(calculationRequest));	
		boolean result = false;
		JSONArray ruleOutputs = null;
		JSONObject rule = null;
		if(ruleResponse != null && !StringUtils.isEmpty(ruleResponse)) {
		JSONArray rules = new JSONArray(ruleResponse);
		for (int i = 0; i < rules.length(); i++) {
			rule = rules.getJSONObject(i);
			JSONArray conditions = rule.getJSONArray("conditions");
			for (int j = 0; j < conditions.length(); j++) {
				JSONObject condition = conditions.getJSONObject(j);
				String condExpression = condition.getString("condition");
				JSONArray parameters = condition.getJSONArray("parameters");
				JexlExpression e = jexl.createExpression(condExpression);
				JexlContext context = new MapContext();
				for (int k = 0; k < parameters.length(); k++) {
					String parameter = parameters.getString(k);
					String value = null;
					if (request.has(parameter) && value == null)
						value = request.getString(parameter);
					else if (taxLine.has(parameter) && value == null)
						value = taxLine.getString(parameter);
					context.set(parameter, value);
				}

				result = (boolean) e.evaluate(context);
				if (!result)
					break;
			}
			if (result) {
				ruleOutputs = rule.getJSONArray("outputs");
				LOGGER.info("Condition is evaluated to true with outputs{}",ruleOutputs);
				break;
			}
		}
		}
		return ruleOutputs;
	}
	public JSONArray EvaluateFactor(TaxCalculationRequest calculationRequest, JSONObject taxLine)
			throws JSONException, SQLException {
		
		String factorRule = dbRead.getFactorRule(calculationRequest.getTaxEvent(),calculationRequest.getTaxCountry());
		JSONObject request = new JSONObject(JsonUtil.toJsonString(calculationRequest));
		JSONArray ruleOutputs = null;
		if(factorRule != null) {
		JSONArray rules = new JSONArray(factorRule);
		boolean result = false;
		JSONObject rule = null;

		for (int i = 0; i < rules.length(); i++) {
			rule = rules.getJSONObject(i);
			JSONArray conditions = rule.getJSONArray("conditions");
			for (int j = 0; j < conditions.length(); j++) {
				JSONObject condition = conditions.getJSONObject(j);
				String condExpression = condition.getString("condition");
				JSONArray parameters = condition.getJSONArray("parameters");
				JexlExpression e = jexl.createExpression(condExpression);
				JexlContext context = new MapContext();
				for (int k = 0; k < parameters.length(); k++) {
					String parameter = parameters.getString(k);
					String value = null;
					if (request.has(parameter) && value == null)
						value = request.getString(parameter);
					else if (taxLine.has(parameter) && value == null)
						value = taxLine.getString(parameter);
					context.set(parameter, value);
				}

				result = (boolean) e.evaluate(context);
				if (!result)
					break;
			}
			if (result) {
				ruleOutputs = rule.getJSONArray("outputs");
				LOGGER.info("Condition is evaluated to true with outputs{} for factor",ruleOutputs);
				break;
			}
		}
		}
		return ruleOutputs;
	}
}
