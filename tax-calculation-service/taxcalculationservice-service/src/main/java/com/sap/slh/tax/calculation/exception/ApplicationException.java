package com.sap.slh.tax.calculation.exception;

import java.util.Map;

import com.sap.slh.tax.calculation.model.api.ProcessingStatusCode;


/**
 * This class is used to handle application level exceptions and extends
 * <code>RuntimeException</code>.
 * 
 * @author 
 */
public class ApplicationException extends RuntimeException {

    /** serialVersionUID. */
    private static final long serialVersionUID = 2837508888552870730L;

    /** The status code. */
    private ProcessingStatusCode processingStatusCode;
    private Map<String, Object> debugInfo;

    public ApplicationException(final String message, final ProcessingStatusCode processingStatusCode) {
        super(message);
        this.processingStatusCode = processingStatusCode;
    }

    public ApplicationException(final String message, final ProcessingStatusCode processingStatusCode,
            Map<String, Object> debugInfo) {
        super(message);
        this.processingStatusCode = processingStatusCode;
        this.debugInfo = debugInfo;
    }

    public ApplicationException(final String message, final Throwable exception,
            final ProcessingStatusCode processingStatusCode) {
        super(message, exception);
        this.processingStatusCode = processingStatusCode;
    }

    public ProcessingStatusCode getProcessingStatusCode() {
        return processingStatusCode;
    }

    public Map<String, Object> getDebugInfo() {
        return debugInfo;
    }
}
