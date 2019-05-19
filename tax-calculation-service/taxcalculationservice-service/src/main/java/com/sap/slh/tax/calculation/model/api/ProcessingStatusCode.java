package com.sap.slh.tax.calculation.model.api;

/**
 * The Enum Class ProcessingStatusCode.
 * 
 */
public enum ProcessingStatusCode {
    
    ACCESS_TOKEN_FETCHED("Access token fetched"),
    ACCESS_TOKEN_VERIFIED("Access token verified"),
    TOKEN_VERIFICATION_FAILURE("Token verification error"),
    MISSING_APP_CONFIGURATION("The configuration for given app is missing"),

    OPERATION_UNSUPPORTED_ERROR("Operation unsupported error occurred"),
    INVALID_PARAMETER("Invalid parameter"),
    URL_ENCODING_ERROR("Url encoding error"),

    ADMIN_OPERATION_SUCCESS("Admin operation successfully executed"),
    SCHEMA_VERSION_ERROR("The existing schema version should be less than the current code version"),
    INTERNAL_SERVER_ERROR("Internal server error"),
    TAX_CALCULATED_SUCCESSFULLY("Tax calculated successfully"),
	TAX_CALCULATION_FAILURE("Unable to calculate tax"),
	NO_RULE_FOUND("Unable to find the rule for calculation");
   
    private String value;

    /**
     * Instantiates a new response value.
     *
     * @param value
     *            the value
     */
    private ProcessingStatusCode(final String value) {
        this.value = value;
    }

    /**
     * Gets the value.
     *
     * @return the value
     */
    public final String getValue() {
        return value;
    }
}
