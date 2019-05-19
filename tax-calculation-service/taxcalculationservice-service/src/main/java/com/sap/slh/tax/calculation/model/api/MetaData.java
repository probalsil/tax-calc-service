package com.sap.slh.tax.calculation.model.api;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * The Class MetaData.
 * 
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class MetaData {

    /** The requested uri. */
    private String requestedUri;

    /** The response time. */
    private String responseTime;

    /** The response status. */
    private String responseStatus;

    /** The request type. */
    private String requestType;

    /** The content type. */
    private String contentType;

    /** The locale. */
    private String locale;

    /** The character encoding. */
    private String characterEncoding;

    /** The when requested. */
    private String whenRequested;

    /** The vstr guid. */
    private String vstrGUID;

    /** The request parameters. */
    private Map<String, String> requestParameters;

    /**
     * Gets the requested uri.
     *
     * @return the requestedUri
     */
    public String getRequestedUri() {
        return requestedUri;
    }

    /**
     * Sets the requested uri.
     *
     * @param requestedUri
     *            the requestedUri to set
     */
    public void setRequestedUri(String requestedUri) {
        this.requestedUri = requestedUri;
    }

    /**
     * Gets the response time.
     *
     * @return the responseTime
     */
    public String getResponseTime() {
        return responseTime;
    }

    /**
     * Sets the response time.
     *
     * @param responseTime
     *            the responseTime to set
     */
    public void setResponseTime(String responseTime) {
        this.responseTime = responseTime;
    }

    /**
     * Gets the response status.
     *
     * @return the responseStatus
     */
    public String getResponseStatus() {
        return responseStatus;
    }

    /**
     * Sets the response status.
     *
     * @param responseStatus
     *            the responseStatus to set
     */
    public void setResponseStatus(String responseStatus) {
        this.responseStatus = responseStatus;
    }

    /**
     * Gets the request type.
     *
     * @return the requestType
     */
    public String getRequestType() {
        return requestType;
    }

    /**
     * Sets the request type.
     *
     * @param requestType
     *            the requestType to set
     */
    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    /**
     * Gets the content type.
     *
     * @return the contentType
     */
    public String getContentType() {
        return contentType;
    }

    /**
     * Sets the content type.
     *
     * @param contentType
     *            the contentType to set
     */
    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    /**
     * Gets the locale.
     *
     * @return the locale
     */
    public String getLocale() {
        return locale;
    }

    /**
     * Sets the locale.
     *
     * @param locale
     *            the locale to set
     */
    public void setLocale(String locale) {
        this.locale = locale;
    }

    /**
     * Gets the character encoding.
     *
     * @return the characterEncoding
     */
    public String getCharacterEncoding() {
        return characterEncoding;
    }

    /**
     * Sets the character encoding.
     *
     * @param characterEncoding
     *            the characterEncoding to set
     */
    public void setCharacterEncoding(String characterEncoding) {
        this.characterEncoding = characterEncoding;
    }

    /**
     * Gets the when requested.
     *
     * @return the whenRequested
     */
    public String getWhenRequested() {
        return whenRequested;
    }

    /**
     * Sets the when requested.
     *
     * @param whenRequested
     *            the whenRequested to set
     */
    public void setWhenRequested(String whenRequested) {
        this.whenRequested = whenRequested;
    }

    /**
     * Gets the vstr guid.
     *
     * @return the vstrGUID
     */
    public String getVstrGUID() {
        return vstrGUID;
    }

    /**
     * Sets the vstr guid.
     *
     * @param vstrGUID
     *            the vstrGUID to set
     */
    public void setVstrGUID(String vstrGUID) {
        this.vstrGUID = vstrGUID;
    }

    /**
     * Gets the request parameters.
     *
     * @return the requestParameters
     */
    public Map<String, String> getRequestParameters() {
        return requestParameters;
    }

    /**
     * Sets the request parameters.
     *
     * @param requestParameters
     *            the requestParameters to set
     */
    public void setRequestParameters(Map<String, String> requestParameters) {
        this.requestParameters = requestParameters;
    }

}
