package com.sap.slh.tax.calculation.utility;

import org.apache.commons.lang.RandomStringUtils;

/**
 * This is utility class to generate unique token for Errors.
 * 
 * @author 
 */
public final class ErrorTokenGenerator {

    /** The length of the unique string to be generated. */
    private static final int UNIQUE_STRING_LENGTH = 10;

    /** ApplicationError token prefix. */
    private static final String ERROR_TOKEN_PREFIX = "TAXDET-";

    /** Private Constructor. */
    private ErrorTokenGenerator() {
    }

    /**
     * This method generates a random error token.
     *
     * @return String - error token
     */
    public static String getErrorId() {
        return ERROR_TOKEN_PREFIX + getUniqueString();
    }

    /**
     * This method returns a unique string.
     *
     * @return String.
     */
    private static String getUniqueString() {
        return RandomStringUtils.random(UNIQUE_STRING_LENGTH, true, true);
    }

}
