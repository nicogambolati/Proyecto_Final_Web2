package com.ecommers.exceptions;

import org.springframework.http.HttpStatus;

public class BaseECommersException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	public static HttpStatus STATUS_CODE = HttpStatus.INTERNAL_SERVER_ERROR;
	public static String ERROR_CODE;
	public static String ERROR_MSG;
	
	public BaseECommersException() {
		this (ERROR_MSG);
	}

	public BaseECommersException(String _message) {
		super (_message);
		ERROR_MSG = _message;
	}
	
	public BaseECommersException(HttpStatus _status, String _code, String _message) {
		this(_message);
		STATUS_CODE = _status;
		ERROR_CODE = _code;
	}
	
	public String getErrorCode() {
		return ERROR_CODE;
	}

	public String getErrorMessage() {
		return ERROR_MSG;
	}
	
	public HttpStatus getStatusCode() {
		return STATUS_CODE;
	}
}
