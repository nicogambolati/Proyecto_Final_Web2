package com.ecommers.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "error_code", "message" })
public class ErrorAPI {

	@JsonProperty("error_code")
	private String errorCode;
	private String message;
	
	public ErrorAPI() {
		super();
	}
	
	public ErrorAPI(String errorCode, String message) {
		super();
		this.errorCode = errorCode;
		this.message = message;
	}
	
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
}

