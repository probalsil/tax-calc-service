package com.sap.slh.tax.calculation.controller;

import java.io.IOException;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sap.slh.tax.calculation.TaxCalculationService;
import com.sap.slh.tax.calculation.model.api.ProcessingStatusCode;
import com.sap.slh.tax.calculation.model.api.Response;
import com.sap.slh.tax.calculation.model.api.Status;
import com.sap.slh.tax.calculation.model.common.TaxCalculationRequest;
import com.sap.slh.tax.calculation.model.common.TaxCalculationResponse;
import com.sap.slh.tax.calculation.model.uri.TaxCalculationUriConstant;
import com.sap.slh.tax.calculation.utility.JsonUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController @Api(value="tax calculation operations")

public class TaxCalculationController extends BaseAPIController {

	private static final Logger LOGGER = LoggerFactory.getLogger(TaxCalculationController.class);

	@Autowired
	private TaxCalculationService taxCalculationService;
	
	@ApiOperation(value = "calculate tax", notes = "calculate tax", nickname = "calculate tax", response = Response.class)
	@RequestMapping(value = TaxCalculationUriConstant.CALCULATE_TAX, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Response<TaxCalculationResponse> calculate(
			@Valid @RequestBody(required = true) final String taxCalculationRequest) throws JsonParseException, JsonMappingException, IOException {
		
		    LOGGER.info("Calculate tax request data : {}",taxCalculationRequest);  
		    ObjectMapper mapper = new ObjectMapper();
		    List<TaxCalculationRequest> taxCalculationRequestList = mapper.readValue(taxCalculationRequest, new TypeReference<List<TaxCalculationRequest>>(){});
		   
			List<TaxCalculationResponse> response = taxCalculationService
					.calculateTax(taxCalculationRequestList);	
			Response<TaxCalculationResponse> resource = new Response<TaxCalculationResponse>();
			resource.setResult(response);
			resource.setStatus(Status.SUCCESS);
			resource.setStatusMessage(ProcessingStatusCode.TAX_CALCULATED_SUCCESSFULLY.getValue());
			resource.setProcessingStatusCode(ProcessingStatusCode.TAX_CALCULATED_SUCCESSFULLY);
			
			
			LOGGER.info("Calculate tax request data : {} response : {} ",
					JsonUtil.toJsonString(taxCalculationRequest), JsonUtil.toJsonString(response));
			
		return resource;
	}

}
