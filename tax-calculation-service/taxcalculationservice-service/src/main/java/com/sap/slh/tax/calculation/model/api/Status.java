package com.sap.slh.tax.calculation.model.api;

/**
 * The Enum Status.
 *
 */
public enum Status {

    /** The success value response. */
    SUCCESS("Operation is successful."),

    /** The failure value response. */
    FAILURE("Operation Failed.");

    /** The string value of given enum status. */
    private String value;

    /**
     * Instantiates a new response value.
     *
     * @param value
     *            the value
     */
    Status(final String value) {
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
