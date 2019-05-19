package com.sap.slh.tax.calculation;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.sap.slh.tax.calculation.dto.TaxCalculationInputDto;
import com.sap.slh.tax.calculation.dto.TaxCalculationOutputDto;
import com.sap.slh.tax.calculation.exception.InvalidParameterException;
import com.sap.slh.tax.calculation.model.api.DebugInfo;
import com.sap.slh.tax.calculation.model.common.TaxCalculationRequest;
import com.sap.slh.tax.calculation.model.common.TaxCalculationResponse;
import com.sap.slh.tax.calculation.model.uri.PathParamConstant;
import com.sap.slh.tax.calculation.ruleEngine.TaxCalculationResponseBuilder;
import com.sap.slh.tax.calculation.utility.JsonUtil;

@Service
public class TaxCalculationServiceImpl implements TaxCalculationService {
    
	private static final Logger LOGGER = LoggerFactory.getLogger(TaxCalculationServiceImpl.class);
	@Autowired
	private GenericTaxCalculationService genericTaxCalculationService;
	
	@Autowired
	private TaxCalculationResponseBuilder responseBuilder;
	
	@Override
	public List<TaxCalculationResponse> calculateTax(
			final List<TaxCalculationRequest> taxcalculationRequest) {
		List<TaxCalculationResponse> response = new ArrayList<>();		
		LOGGER.debug("Service implementation is invoked");
		if (taxcalculationRequest != null) {
			List<TaxCalculationOutputDto> outputList = genericTaxCalculationService.calculateTax(taxcalculationRequest);
			if(outputList != null && !CollectionUtils.isEmpty(outputList))
			{
				response = responseBuilder.buildResponse(taxcalculationRequest, outputList);
			}
		} else {

			throw new InvalidParameterException(new DebugInfo.DebugInfoBuilder()
					.put(PathParamConstant.TAX_FETCH_REQUEST_DATA, JsonUtil.toJson(taxcalculationRequest)).build());
		}
		return response;

	}

}
