package com.sap.slh.tax.calculation.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.sap.slh.tax.calculation.exception.ApplicationException;
import com.sap.slh.tax.calculation.exception.ExceptionContainer;
import com.sap.slh.tax.calculation.exception.GlobalExceptionHandler;
import com.sap.slh.tax.calculation.model.api.MetaData;
import com.sap.slh.tax.calculation.model.api.ProcessingStatusCode;
import com.sap.slh.tax.calculation.model.api.Response;



/**
 * The aspect that is meant to intercept all the request entering the
 * controller, this aspect is well utilized to compute the metadata and add
 * metadata to outgoing response from apis.
 *
 * */

@Aspect
@Component
public class ControllerAspect {

    /** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(TaxCalculationController.class);

    @Autowired
    private GlobalExceptionHandler globalExceptionHandler;

    /** The Constant CHARSET. */
    private static final String CHARSET = "; charset= ";

    /** The Constant MS. */
    private static final String MS = "ms";

    /** The Constant DATE_FORMAT_METADATA. */
    private static final String DATE_FORMAT_METADATA = "EE MMM dd hh:mm:ss z yyyy";

    /** The Constant CONTROLLER_BEAN_METHOD_POINTCUT. */
    private static final String CONTROLLER_BEAN_METHOD_POINTCUT = "controllerBean() && methodPointcut() ";

    /** The Constant EXECUTION_FOR_ALL_METHODS. */
    private static final String EXECUTION_FOR_ALL_METHODS = "execution(public * *(..))";

    /** The Constant EXECUTION_COM_SAP_TAXCALC_WEB_CONTROLLER. */
    private static final String EXECUTION_COM_SAP_TAXCALC_WEB_CONTROLLER = "execution( * com.sap.slh.tax.calculation.controller..*.*(..))";

    /**
     * Controller bean.
     */
    @Pointcut(EXECUTION_COM_SAP_TAXCALC_WEB_CONTROLLER)
    public void controllerBean() {
        LOGGER.debug("EXECUTION_COM_SAP_TAXCALC_WEB_CONTROLLER");
    }

    /**
     * Method pointcut.
     */
    @Pointcut(EXECUTION_FOR_ALL_METHODS)
    public void methodPointcut() {
        LOGGER.debug("EXECUTION_FOR_ALL_METHODS");
    }

    /**
     * AroundMethodAdvice for the APIs exposed from controller. Intercepts the
     * request, gather requested time, requested uri, request type; also
     * intercepts outgoing response gather information around the response
     * status before releasing it to the consumer.
     *
     * @param joinPoint
     *            the join point
     * @return ResponseBody response.
     * @throws Throwable
     *             the throwable
     */
    @SuppressWarnings("rawtypes")
    @Around(CONTROLLER_BEAN_METHOD_POINTCUT)
    public @ResponseBody Response aroundMethodInControllerClass(ProceedingJoinPoint joinPoint) throws Throwable {
        LOGGER.debug("Got Request for controller");
        Response jsonResponse = null;
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();
        // requested time
        long requestTime = System.currentTimeMillis();
        try {
            jsonResponse = (Response) joinPoint.proceed();
        } catch (ApplicationException apex) {
            jsonResponse = globalExceptionHandler.processApplicationException(apex);
        } catch (ExceptionContainer apex) {
            jsonResponse = globalExceptionHandler.handleContainerException(apex);
        }  catch (IllegalArgumentException iaex) {
            jsonResponse = globalExceptionHandler.processIllegalArgumentException(iaex);
        } catch (Exception ex) {
            jsonResponse = globalExceptionHandler.processException(ex, ProcessingStatusCode.INTERNAL_SERVER_ERROR);
        }

        if (jsonResponse == null) {
            jsonResponse = new Response();
        }
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getResponse();
        long responseTime = System.currentTimeMillis();
        LOGGER.debug("Constructing Metadata");
        MetaData data = constructMetadata(request, requestTime, response, responseTime);
        jsonResponse.setMetaData(data);
        LOGGER.debug("Returning response : {} ", jsonResponse);
        return jsonResponse;
    }

    /**
     * Construct metadata.
     *
     * @param request
     *            the request
     * @param requestTime
     *            the request time
     * @param response
     *            the response
     * @param responseTime
     *            the response time
     * @return the meta data
     */
    private MetaData constructMetadata(HttpServletRequest request, long requestTime, HttpServletResponse response,
            long responseTime) {
        LOGGER.debug("Constructing Metadata for url: {} ", request.getRequestURL());
        MetaData data = new MetaData();
        data.setRequestedUri(request.getRequestURI());
        data.setRequestType(request.getMethod());
        data.setCharacterEncoding(response.getCharacterEncoding());
        data.setVstrGUID(request.getHeader("VGUID"));
        populateRequestParameters(request, data);
        data.setResponseStatus(String.valueOf(response.getStatus()));
        data.setResponseTime(String.valueOf(responseTime - requestTime) + MS);
        data.setContentType(request.getContentType() + CHARSET + response.getCharacterEncoding());
        data.setLocale(response.getLocale().getDisplayName());
        SimpleDateFormat form = new SimpleDateFormat(DATE_FORMAT_METADATA);
        form.format(new Date(requestTime));
        data.setWhenRequested(String.valueOf(form.format(new Date(requestTime))));
        LOGGER.debug("Metadata for url {} is {} ", request.getRequestURL(), data);
        return data;
    }

    /**
     * Populate request parameters.
     *
     * @param request
     *            the request
     * @param data
     *            the data
     */
    private void populateRequestParameters(HttpServletRequest request, MetaData data) {
        LOGGER.debug("Populating request parameters for url: {}", request.getRequestURL());
        Enumeration<String> parameterNames = request.getParameterNames();
        Map<String, String> requestVariable = new HashMap<String, String>();
        while (parameterNames.hasMoreElements()) {
            String paramName = parameterNames.nextElement();
            String[] paramValues = request.getParameterValues(paramName);
            String paramValue = "";
            for (int i = 0; i < paramValues.length; i++) {
                String temp = paramValues[i];
                if (i > 0) {
                    paramValue = temp + " & " + paramValue;
                } else {
                    paramValue = temp;
                }
            }
            requestVariable.put(paramName, paramValue);
        }
        data.setRequestParameters(requestVariable);
        LOGGER.debug("Request parameters for url {} are {}", request.getRequestURL(), data.getRequestParameters());
    }
}
