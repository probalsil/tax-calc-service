package com.sap.slh.tax.calculation.service;

import java.util.List;

import com.sap.slh.tax.calculation.dto.TaxCalculationInputDto;
import com.sap.slh.tax.calculation.dto.TaxCalculationOutputDto;
import com.sap.slh.tax.calculation.model.common.TaxCalculationRequest;
import com.sap.slh.tax.calculation.model.common.TaxCalculationResponse;

public interface TaxCalculationLookupService {

	public TaxCalculationResponse get(
			TaxCalculationRequest taxcalculationRequest);

	public TaxCalculationResponse put(
			TaxCalculationRequest taxcalculationRequest,
			TaxCalculationResponse response);

}
