package com.sap.slh.tax.calculation.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sap.slh.tax.calculation.TaxCalculationService;
import com.sap.slh.tax.calculation.TaxCalculationServiceImpl;
import com.sap.slh.tax.calculation.controller.TaxCalculationController;
import com.sap.slh.tax.calculation.exception.ErrorStatus;
import com.sap.slh.tax.calculation.model.common.TaxCalculationRequest;
import com.sap.slh.tax.calculation.model.common.TaxCalculationResponse;
import com.sap.slh.tax.calculation.utility.JsonUtil;

@Component
public class TaxCalculationListener {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(TaxCalculationListener.class);
	
	@Autowired
	private TaxCalculationService taxCalculationService;
	
	@RabbitListener(queues = "${rabbitmq.calc.queue}",containerFactory = "remoteContainerFactory")
    public String calculateTaxes(String taxCalculationRequest)
    {
	    LOGGER.info("Calculate tax request data : {}",taxCalculationRequest);  
	    List<TaxCalculationResponse> response = new ArrayList<TaxCalculationResponse>();
	    ErrorStatus errorStatus = null;
	    try {
	    ObjectMapper mapper = new ObjectMapper();
	    List<TaxCalculationRequest> taxCalculationRequestList = mapper.readValue(taxCalculationRequest, new TypeReference<List<TaxCalculationRequest>>(){});
	   
		response = taxCalculationService
				.calculateTax(taxCalculationRequestList);	
	    }catch(Exception e)
	    {   
	    	LOGGER.error("Error occured in Tax Calculation ",e);
	    	errorStatus = new ErrorStatus(500,"server_exception", "Failure of Tax Calculation"); 
	    	return JsonUtil.toJsonString(errorStatus);
	    }
	    if(response == null && CollectionUtils.isEmpty(response)) {
	    	LOGGER.error(" Calculation tax response : {}",JsonUtil.toJsonString(response));
	    	errorStatus = new ErrorStatus(500,"server_exception", "Failure of Tax Calculation"); 
	    	return JsonUtil.toJsonString(errorStatus);
	    }
	    LOGGER.info(" Calculation tax response : {}",JsonUtil.toJsonString(response));
		return JsonUtil.toJsonString(response);
        
    }

}
