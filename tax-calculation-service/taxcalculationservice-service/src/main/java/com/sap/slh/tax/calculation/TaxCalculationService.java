package com.sap.slh.tax.calculation;

import java.util.List;

import com.sap.slh.tax.calculation.model.common.TaxCalculationRequest;
import com.sap.slh.tax.calculation.model.common.TaxCalculationResponse;

public interface TaxCalculationService {
	
	public List<TaxCalculationResponse> calculateTax(
			final List<TaxCalculationRequest> taxCalculationRequest);

}
