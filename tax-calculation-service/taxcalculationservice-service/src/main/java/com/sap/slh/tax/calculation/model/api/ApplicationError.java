package com.sap.slh.tax.calculation.model.api;

import java.io.Serializable;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * The Class ApplicationError.
 * 
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ApplicationError implements Serializable {
    private static final long serialVersionUID = -8876031063424368365L;
    private String errorId;
    private Map<String, Object> debugInfo;

    public String getErrorId() {
        return errorId;
    }

    public void setErrorId(String errorId) {
        this.errorId = errorId;
    }

    public Map<String, Object> getDebugInfo() {
        return debugInfo;
    }

    public void setDebugInfo(Map<String, Object> debugInfo) {
        this.debugInfo = debugInfo;
    }
}
