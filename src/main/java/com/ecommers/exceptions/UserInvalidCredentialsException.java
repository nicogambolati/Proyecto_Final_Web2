package com.ecommers.exceptions;

import org.springframework.http.HttpStatus;

public class UserInvalidCredentialsException extends BaseECommersException{

	private static final long serialVersionUID = -3739995681650227740L;

	public UserInvalidCredentialsException() {
		super (HttpStatus.BAD_REQUEST, "USER_INVALID_CREDENTIALS", "Invalid credentials");
	}

}
