package com.sap.slh.tax.calculation.exception;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;


import com.sap.slh.tax.calculation.model.BaseModel;
import com.sap.slh.tax.calculation.model.api.ApplicationError;
import com.sap.slh.tax.calculation.model.api.ProcessingStatusCode;
import com.sap.slh.tax.calculation.model.api.Response;
import com.sap.slh.tax.calculation.model.api.Status;
import com.sap.slh.tax.calculation.utility.ErrorTokenGenerator;
import com.sap.slh.tax.calculation.utility.JsonUtil;

/**
 * General error handler for the application.
 * 
 */
@ControllerAdvice
public class GlobalExceptionHandler {

	/** Logger instance. */
	private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@Value(value = "${tax_calculation.logger.stack_trace_line_limit:10}")
	private Long logStackTraceLineLimit;

	/**
	 * Handle global http request method not supported.
	 *
	 * @param exception the exception
	 * @return the response
	 */
	// Does not call aspect
	@ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
	@ResponseBody
	@ResponseStatus(value = HttpStatus.OK)
	public Response<BaseModel> handleGlobalHttpRequestMethodNotSupported(final Exception exception) {
		return processException(exception, ProcessingStatusCode.OPERATION_UNSUPPORTED_ERROR);
	}

	/**
	 * Handle global application exception.
	 *
	 * @param exception the exception
	 * @return the response
	 */
	@ExceptionHandler(value = ApplicationException.class)
	@ResponseBody
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public Response<BaseModel> handleGlobalApplicationException(final ApplicationException exception) {
		return processApplicationException(exception);
	}

	/**
	 * Handle global illegal argument exception.
	 *
	 * @param exception the exception
	 * @return the response
	 */
	@ExceptionHandler(value = IllegalArgumentException.class)
	@ResponseBody
	@ResponseStatus(value = HttpStatus.OK)
	public Response<BaseModel> handleGlobalIllegalArgumentException(final IllegalArgumentException exception) {
		return processIllegalArgumentException(exception);
	}

	/**
	 * Handle global general exception.
	 *
	 * @param exception the exception
	 * @return the response
	 */
	@ExceptionHandler(value = Exception.class)
	@ResponseBody
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public Response<BaseModel> handleGlobalGeneralException(final Exception exception) {
		return processException(exception, ProcessingStatusCode.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(value = HttpMessageNotReadableException.class)
	@ResponseBody
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public Response<BaseModel> handleGlobalHttpMessageNotReadableException(
			final HttpMessageNotReadableException exception) {
		return processException(exception, ProcessingStatusCode.INTERNAL_SERVER_ERROR);
	}

	/**
	 * This method is handler for MethodArgumentNotValidException Exceptions.
	 *
	 * @param validationException that has been thrown
	 * @return ErrorResponse response to send back
	 */
	// Does not call aspect
	@ExceptionHandler(value = MethodArgumentNotValidException.class)
	@ResponseBody
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public Response<BaseModel> handleValidationException(final MethodArgumentNotValidException exception) {
		Response<BaseModel> response = new Response<BaseModel>();
		Map<String, Object> debugInfo = getBindingResultString(exception.getBindingResult());
		response.setProcessingStatusCode(ProcessingStatusCode.INVALID_PARAMETER);
		response.setError(getApplicationError(debugInfo));
		response.setStatusMessage(ProcessingStatusCode.INVALID_PARAMETER.getValue());
		response.setStatus(Status.FAILURE);
		LOGGER.error(JsonUtil.toJsonString(response), exception);
		return response;
	}

	private ApplicationError getApplicationError() {
		ApplicationError applicationError = new ApplicationError();
		applicationError.setErrorId(ErrorTokenGenerator.getErrorId());
		return applicationError;
	}

	private ApplicationError getApplicationError(final Map<String, Object> debugInfo) {
		ApplicationError applicationError = getApplicationError();
		applicationError.setDebugInfo(debugInfo);
		return applicationError;
	}

	/**
	 * This method is handler for BindException Exceptions.
	 *
	 * @param bindException that has been thrown
	 * @return ErrorResponse response to send back
	 */
	// Does not call aspect
	@ExceptionHandler(value = BindException.class)
	@ResponseBody
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public Response<BaseModel> handleBindingException(final BindException exception) {
		Response<BaseModel> response = new Response<BaseModel>();
		Map<String, Object> debugInfo = getBindingResultString(exception.getBindingResult());
		response.setProcessingStatusCode(ProcessingStatusCode.INTERNAL_SERVER_ERROR);
		response.setError(getApplicationError(debugInfo));
		response.setStatusMessage(ProcessingStatusCode.INVALID_PARAMETER.getValue());
		response.setStatus(Status.FAILURE);
		LOGGER.error(JsonUtil.toJsonString(response), exception);
		return response;
	}

	private Map<String, Object> getBindingResultString(final BindingResult bindingResult) {
		Map<String, Object> debugInfo = new HashMap<String, Object>();
		final List<FieldError> validationErrors = bindingResult.getFieldErrors();
		for (FieldError fieldError : validationErrors) {
			debugInfo.put(fieldError.getField(), fieldError.getDefaultMessage());
		}
		return debugInfo;
	}

	/**
	 * Process application exception.
	 *
	 * @param exception the exception
	 * @return the response
	 */
	public Response<BaseModel> processApplicationException(ApplicationException exception) {
		Response<BaseModel> response = new Response<BaseModel>();
		response.setError(getApplicationError(exception.getDebugInfo()));
		response.setStatusMessage(exception.getMessage());
		response.setProcessingStatusCode(exception.getProcessingStatusCode());
		response.setStatus(Status.FAILURE);
		String limitedStackTrace = getLimitedStackTrace(exception.getStackTrace());
		LOGGER.error("\nAPPLICATION ERROR : {} \nSTACK TRACE : \n{}", JsonUtil.toJsonString(response),
				limitedStackTrace);
		return response;
	}

	private String getLimitedStackTrace(StackTraceElement[] stackTraceElements) {
		if (stackTraceElements != null && stackTraceElements.length > 0) {
			StringBuffer stringBuffer = new StringBuffer();
			for (int i = 0; i < logStackTraceLineLimit && i < stackTraceElements.length; i++) {
				stringBuffer.append(stackTraceElements[i]).append("\n");
			}
			return stringBuffer.toString();
		}
		return null;
	}

	/**
	 * Process illegal argument exception.
	 *
	 * @param exception the exception
	 * @return the response
	 */
	public Response<BaseModel> processIllegalArgumentException(IllegalArgumentException exception) {
		Response<BaseModel> response = new Response<BaseModel>();
		response.setError(getApplicationError());
		response.setStatusMessage(exception.getMessage());
		response.setProcessingStatusCode(ProcessingStatusCode.INVALID_PARAMETER);
		response.setStatus(Status.FAILURE);
		LOGGER.error(JsonUtil.toJsonString(response), exception);
		return response;
	}


	public Response<BaseModel> processException(Exception exception, ProcessingStatusCode processingStatusCode) {
		Response<BaseModel> response = new Response<BaseModel>();
		response.setError(getApplicationError());
		response.setStatusMessage(exception.getMessage());
		response.setProcessingStatusCode(processingStatusCode);
		response.setStatus(Status.FAILURE);
		LOGGER.error(JsonUtil.toJsonString(response), exception);
		return response;
	}

	public Response<BaseModel> handleContainerException(final ExceptionContainer exception) {
		Response<BaseModel> response = new Response<BaseModel>();
		List<ApplicationException> list = exception.getExceptions();
		Map<String, Object> debugInfomessage = new HashMap<>();
		StringBuffer buffer = new StringBuffer();

		for (ApplicationException exception1 : list) {
			Map<String, Object> debugInfo = exception1.getDebugInfo();
			Set<String> keys = debugInfo.keySet();
			for (String key : keys) {
				String value = (String) debugInfo.get(key);
				if (!StringUtils.isEmpty(value)) {
					if (debugInfomessage.containsKey(key)) {
						StringBuffer buffer1 = new StringBuffer();
						String value1 = (String) debugInfomessage.get(key);
						if (!value.equalsIgnoreCase(value1)) {
							buffer1.append(value1).append(",").append(value);
							debugInfomessage.put(key, buffer1);
						}

					} else {
						debugInfomessage.put(key, debugInfo.get(key));
					}

				}

			}
			buffer.append(exception1.getProcessingStatusCode());
			buffer.append(",");
		}
		response.setProcessingStatusCode(ProcessingStatusCode.INVALID_PARAMETER);
		response.setError(getApplicationError(debugInfomessage));
		response.setStatusMessage(buffer.toString());

		response.setStatus(Status.FAILURE);
		LOGGER.error(JsonUtil.toJsonString(response), exception);
		return response;
	}
}
