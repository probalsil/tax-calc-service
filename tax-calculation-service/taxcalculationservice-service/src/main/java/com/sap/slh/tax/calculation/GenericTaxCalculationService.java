package com.sap.slh.tax.calculation;

import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.sap.slh.tax.calculation.dto.TaxCalculationInputDto;
import com.sap.slh.tax.calculation.dto.TaxCalculationOutputDto;
import com.sap.slh.tax.calculation.model.common.TaxCalculationRequest;
import com.sap.slh.tax.calculation.model.common.TaxCalculationResponse;
import com.sap.slh.tax.calculation.utility.JsonUtil;

@Service("genericTaxCalculationService")
public class GenericTaxCalculationService extends TaxCalculationServiceChain {

	private static final Logger LOGGER = LoggerFactory.getLogger(GenericTaxCalculationService.class);

	@Autowired
	@Qualifier("cacheTaxCalculationService")
	TaxCalculationServiceChain cacheTaxCalculationService;

	@Autowired
	@Qualifier("ruleEngineTaxCalculationService")
	TaxCalculationServiceChain ruleEngineTaxCalculationService;

	/**
	 * Initialize service chain.
	 */
	@PostConstruct
	public void initialiseAddrNormalisationServiceChain() {
		this.setSuccessor(cacheTaxCalculationService);
		cacheTaxCalculationService.setSuccessor(ruleEngineTaxCalculationService);
	}

	@Override
	public List<TaxCalculationOutputDto> calculateTax(
			List<TaxCalculationRequest> taxcalculationRequest) {
		LOGGER.info("calculateTaxRequest : {}", JsonUtil.toJsonString(taxcalculationRequest));
		List<TaxCalculationOutputDto> outputList = null;
		
		if (isValid(taxcalculationRequest)) {
			outputList = successor.calculateTax(taxcalculationRequest);
		}
		return outputList;
	}
}
