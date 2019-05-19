package com.sap.slh.tax.calculation.utility;

import java.io.IOException;

import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sap.slh.tax.calculation.exception.ApplicationException;
import com.sap.slh.tax.calculation.model.api.ProcessingStatusCode;


/**
 * Provides utility method for Object to Json conversion.
 *
 * @author 
 *
 */
public final class JsonUtil {

    /**
     * Private Constructor.
     */
    private JsonUtil() {
    }

    /**
     * This converts Object of type E to its JSON form.
     *
     * @param <E>
     *            the element type
     * @param type
     *            - The type of Object.
     * @return - JSON String.
     */
    public static <E> String toJson(final E type) {
        final String jsonValue = toJsonString(type);

        return jsonValue == null ? null : "[" + jsonValue + "]";

    }

    /**
     * This converts JSON to Object of type E.
     *
     * @param <E>
     *            the element type
     * @param responseEntity
     *            - Instance of {@link ResponseEntity}.
     * @param clazz
     *            - instance of {@link Class}.
     * @return - Object of type E.
     */
    public static <E> Object toObject(final ResponseEntity<String> responseEntity, final Class<E> clazz) {

        E object = toObject(responseEntity.getBody(), clazz);

        return object == null ? null : object;

    }

    /**
     * This converts JSON to Object of type E.
     *
     * @param <E>
     *            the element type
     * @param jsonString
     *            - String to be converted to JSON.
     * @param clazz
     *            - instance of {@link Class}.
     * @return - Object of type E.
     */
    public static <E> E toObject(final String jsonString, final Class<E> clazz) {

        E object = null;
        final ObjectMapper propertyMapper = new ObjectMapper();
        propertyMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            object = propertyMapper.readValue(jsonString, clazz);
        } catch (IOException exception) {
            final String errorMsg = "Json to Object transformation failed: " + jsonString;
            throw new ApplicationException(errorMsg, exception, ProcessingStatusCode.INTERNAL_SERVER_ERROR);
        }
        return object;
    }

    /**
     * This converts Object of type E to its JSON string.
     *
     * @param <E>
     *            the element type
     * @param type
     *            - The type of Object.
     * @return - JSON String.
     */
    public static <E> String toJsonString(final E type) {
        final E object = type;
        String jsonValue = null;
        if (object != null) {
            try {
                final ObjectMapper propertyMapper = new ObjectMapper();
                propertyMapper.setSerializationInclusion(Include.NON_NULL);
                jsonValue = propertyMapper.writeValueAsString(object);
            } catch (IOException exception) {
                final String errorMsg = "Object to Json transformation failed : " + object.toString();
                throw new ApplicationException(errorMsg, exception, ProcessingStatusCode.INTERNAL_SERVER_ERROR);
            }
        }
        return jsonValue;
    }

}
