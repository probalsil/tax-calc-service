package com.sap.slh.tax.calculation.model.api;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sap.slh.tax.calculation.model.common.TaxCalculationResponse;

/**
 * The Class Response.
 * 
 * @param <T>
 *            the generic type
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Response<T> implements Serializable {

	private static final long serialVersionUID = -1710728014301374638L;

	private Status status;

    /** The status code. */
    private ProcessingStatusCode processingStatusCode;

    /** The status message. */
    private String statusMessage;

    /** The meta data. */
    private MetaData metaData;

    /** The result. */
    private List<TaxCalculationResponse> result;

    private ApplicationError error;

    /**
     * Gets the meta data.
     *
     * @return the metaData
     */
    public MetaData getMetaData() {
        return metaData;
    }

    /**
     * Sets the meta data.
     *
     * @param metaData
     *            the metaData to set
     */
    public void setMetaData(MetaData metaData) {
        this.metaData = metaData;
    }

    /**
     * Gets the result.
     *
     * @return the result
     */
    public List<TaxCalculationResponse> getResult() {
        return result;
    }

    /**
     * Sets the result.
     *
     * @param response
     *            the result to set
     */
    public void setResult(List<TaxCalculationResponse> response) {
        this.result = response;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public ProcessingStatusCode getProcessingStatusCode() {
        return processingStatusCode;
    }

    public void setProcessingStatusCode(ProcessingStatusCode processingStatusCode) {
        this.processingStatusCode = processingStatusCode;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public ApplicationError getError() {
        return error;
    }

    public void setError(ApplicationError applicationError) {
        this.error = applicationError;
    }
}
