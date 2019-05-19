package com.sap.slh.tax.calculation.exception;

import java.util.Map;

import org.springframework.aop.AopInvocationException;

import com.sap.slh.tax.calculation.model.api.ProcessingStatusCode;

public class InvalidParameterException extends AopInvocationException {
	
	private static final long serialVersionUID = 7851868378332160097L;

	public InvalidParameterException(Map<String, Object> debugInfo) {
		super(ProcessingStatusCode.INVALID_PARAMETER.getValue());
	}

}
