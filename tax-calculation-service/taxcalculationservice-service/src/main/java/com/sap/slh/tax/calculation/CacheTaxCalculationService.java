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
import com.sap.slh.tax.calculation.model.common.TaxCalculationRequest;
import com.sap.slh.tax.calculation.model.common.TaxCalculationResponse;
import com.sap.slh.tax.calculation.service.TaxCalculationLookupService;

/**
 * The Class CacheTaxCalculationService.
 * 
 * @author 
 */
@Service("cacheTaxCalculationService")
public class CacheTaxCalculationService extends TaxCalculationServiceChain {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CacheTaxCalculationService.class);

	@Autowired
	TaxCalculationLookupService cacheTaxCalculationLookupService;
	List<TaxCalculationRequest> taxCalculationRequests = new ArrayList<TaxCalculationRequest>();

	/*private void putInCache(List<TaxCalculationRequest> taxcalculationRequest,
			TaxCalculationOutputDto taxCalculationOutputDto) {
		cacheTaxCalculationLookupService.put(taxcalculationRequest, taxCalculationOutputDto);
	}*/

	@Override
	public List<TaxCalculationOutputDto> calculateTax(
			List<TaxCalculationRequest> taxcalculationRequest) {
		List<TaxCalculationOutputDto> outputList = new ArrayList<>();
		if (isValid(taxcalculationRequest)) {
			//taxCalculationResponse = getFromCache(taxcalculationRequest);
			
				List<TaxCalculationOutputDto> output = successor.calculateTax(taxcalculationRequest);
				outputList.addAll(output);
				//putInCache(taxcalculationRequests, taxCalculationResponse);
			
		}

		return outputList;
	}

	private List<TaxCalculationResponse> getFromCache(
			List<TaxCalculationRequest> taxcalculationRequestList) {
		List<TaxCalculationResponse> responseList = new ArrayList<TaxCalculationResponse>();
		//TaxCalculationResponse response;
		taxcalculationRequestList.stream().forEach(taxCalculationRequest -> {
		TaxCalculationResponse response =  cacheTaxCalculationLookupService.get(taxCalculationRequest);
		if(response != null)
			responseList.add(response);
		else
			taxCalculationRequests.add(taxCalculationRequest);
		});
		
		return responseList;

	}
}