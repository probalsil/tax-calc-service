package com.sap.slh.tax.calculation.utility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service("restServiceUtil")
public class RestServiceUtil {

	private static final Logger LOGGER = LoggerFactory.getLogger(RestServiceUtil.class);

	private RestTemplate taxDeterminationRestTemplate;

	public void postEntity(final String url, final String jsonValue) {
		LOGGER.debug("Hitting post url : {} with json value : {}", url, jsonValue);
		final HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		try {
			final HttpEntity<String> entity = new HttpEntity<String>(jsonValue, headers);
			taxDeterminationRestTemplate.postForLocation(url, entity);
		} catch (RestClientException rce) {
			LOGGER.error("Got exception while posting entity to : {} ", url, rce);
			if (rce instanceof HttpStatusCodeException) {
				String errorCause = ((HttpStatusCodeException) rce).getResponseBodyAsString();
				LOGGER.error("Error while hitting url : " + url + " errorCause : " + errorCause, rce);
			}
			throw rce;
		}
		LOGGER.debug("Hit for post url : {} is completed", url);
	}

	public <T> T postEntityWithGenericResponse(String url, Object jsonObject, ParameterizedTypeReference<T> clazz) {
		ObjectMapper objectMapper = new ObjectMapper();
		String json;
		try {
			json = objectMapper.writeValueAsString(jsonObject);
			return postEntityWithGenericResponse(url, json, clazz);
		} catch (JsonProcessingException e) {
			LOGGER.error("Error while serializing object :" + jsonObject, e);
		}
		return null;
	}

	public <T> T postEntityWithGenericResponse(String url, String jsonValue, ParameterizedTypeReference<T> clazz) {
		LOGGER.debug("Hitting post url : {} with json value : {}", url, jsonValue);
		final HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		try {
			final HttpEntity<String> entity = new HttpEntity<String>(jsonValue, headers);
			ResponseEntity<T> response = taxDeterminationRestTemplate.exchange(url, HttpMethod.POST, entity, clazz);
			return response.getBody();
		} catch (RestClientException rce) {
			LOGGER.error("Got exception while posting entity to : {} ", url, rce);
			if (rce instanceof HttpStatusCodeException) {
				String errorCause = ((HttpStatusCodeException) rce).getResponseBodyAsString();
				LOGGER.error("Error while hitting url : " + url + " errorCause : " + errorCause, rce);
			}
			throw rce;
		}
	}

}
