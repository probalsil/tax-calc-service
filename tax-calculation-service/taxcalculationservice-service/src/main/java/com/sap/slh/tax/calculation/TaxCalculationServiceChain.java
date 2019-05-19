package com.sap.slh.tax.calculation;


import java.util.List;

import com.sap.slh.tax.calculation.dto.TaxCalculationInputDto;
import com.sap.slh.tax.calculation.dto.TaxCalculationOutputDto;
import com.sap.slh.tax.calculation.exception.ApplicationException;
import com.sap.slh.tax.calculation.model.api.ProcessingStatusCode;
import com.sap.slh.tax.calculation.model.common.TaxCalculationRequest;
import com.sap.slh.tax.calculation.model.common.TaxCalculationResponse;


public abstract class TaxCalculationServiceChain {

	/** The successor. */
	protected TaxCalculationServiceChain successor;

	/**
	 * Sets the successor.
	 *
	 * @param successor the new successor
	 */
	public void setSuccessor(TaxCalculationServiceChain successor) {
		this.successor = successor;
	}

	/**
	 * .
	 *
	 * @param taxAttributesDeterminationInputDto
	 * @return the TaxAttributesDeterminationOutputDto
	 */
	abstract public List<TaxCalculationOutputDto> calculateTax(
			List<TaxCalculationRequest> taxcalculationRequest);

	/**
	 * Checks if is valid.
	 *
	 * @param taxCalculationInputDto the tax calculation
	 *                                           request
	 * @return true, if is valid
	 */
	protected boolean isValid(List<TaxCalculationRequest> taxcalculationRequest) {
		if (taxcalculationRequest != null) {
			return true;
		}
		throw new ApplicationException(ProcessingStatusCode.TAX_CALCULATION_FAILURE.getValue(),
				ProcessingStatusCode.TAX_CALCULATION_FAILURE);
	}

}