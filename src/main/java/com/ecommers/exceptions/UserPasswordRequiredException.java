package com.ecommers.exceptions;

import org.springframework.http.HttpStatus;

public class UserPasswordRequiredException extends BaseECommersException {

	private static final long serialVersionUID = -6050757414258761450L;

	public UserPasswordRequiredException() {
		super (HttpStatus.BAD_REQUEST, "USER_PASSWORD_REQUIRED", "User password is requiredy");
	}

}
