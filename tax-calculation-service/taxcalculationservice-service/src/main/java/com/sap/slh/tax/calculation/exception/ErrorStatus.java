package com.sap.slh.tax.calculation.exception;

import java.util.List;

import javax.lang.model.type.ErrorType;

public class ErrorStatus {

	int status;
	String type;
	String message;
	String moreinfo;
	List<Details> details;
	
	public ErrorStatus() {
		
	}
	
	public ErrorStatus(int status, String type, String message) {
		super();
		this.status = status;
		this.type = type;
		this.message = message;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getMoreinfo() {
		return moreinfo;
	}

	public void setMoreinfo(String moreinfo) {
		this.moreinfo = moreinfo;
	}

	public List<Details> getDetails() {
		return details;
	}

	public void setDetails(List<Details> details) {
		this.details = details;
	}

}