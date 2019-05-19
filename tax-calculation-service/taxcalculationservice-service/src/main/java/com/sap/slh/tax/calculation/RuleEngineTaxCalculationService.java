package com.sap.slh.tax.calculation;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sap.slh.tax.calculation.dto.TaxCalculationOutputDto;
import com.sap.slh.tax.calculation.exception.ApplicationException;
import com.sap.slh.tax.calculation.model.api.ProcessingStatusCode;
import com.sap.slh.tax.calculation.model.common.TaxCalculationRequest;
import com.sap.slh.tax.calculation.model.common.TaxLine;
import com.sap.slh.tax.calculation.ruleEngine.RuleEvaluation;
import com.sap.slh.tax.calculation.utility.JsonUtil;
import com.sap.slh.tax.calculation.utility.RestServiceUtil;

@Service("ruleEngineTaxCalculationService")
public class RuleEngineTaxCalculationService extends TaxCalculationServiceChain {

	private static final Logger LOGGER = LoggerFactory.getLogger(RuleEngineTaxCalculationService.class);

	@Autowired
	private RestServiceUtil restServiceUtil;

	@Autowired
	private RuleEvaluation ruleEvaluation;

	@Override
	public List<TaxCalculationOutputDto> calculateTax(List<TaxCalculationRequest> taxcalculationRequestList) {
		List<TaxCalculationOutputDto> outputList = new ArrayList<>();

		if (isValid(taxcalculationRequestList)) {
			taxcalculationRequestList.stream().forEach(taxCalculationRequest -> {
				List<TaxLine> taxLines = taxCalculationRequest.getTaxLines();
				taxLines.sort(Comparator.comparing(TaxLine::getTaxType));
				taxLines.forEach(taxline -> {
					try {
						JSONObject taxlineJson = new JSONObject(JsonUtil.toJsonString(taxline));
						JSONArray ruleResponse = ruleEvaluation.EvaluateRule(taxCalculationRequest, taxlineJson);
						JSONArray factorRule = ruleEvaluation.EvaluateFactor(taxCalculationRequest, taxlineJson);
						TaxCalculationOutputDto outputDto = new TaxCalculationOutputDto();
						if (ruleResponse == null)
							throw new ApplicationException(
									ProcessingStatusCode.NO_RULE_FOUND.getValue(),
									ProcessingStatusCode.NO_RULE_FOUND);
							if (ruleResponse != null)
								outputDto.setItemId(taxCalculationRequest.getItemId());
						outputDto.setTaxLineId(taxline.getId());
						outputDto.setOutputs(ruleResponse);
						if (factorRule != null)
							outputDto.setFactor(factorRule);
						outputList.add(outputDto);
						LOGGER.info("The outputs {}", ruleResponse.toString());
						LOGGER.info("The length of outputs in Rule Engine {}", outputList.size());
					} catch (JSONException | SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				});

			});
		}
		return outputList;
	}

}
