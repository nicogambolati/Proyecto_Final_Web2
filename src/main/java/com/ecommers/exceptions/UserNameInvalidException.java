package com.ecommers.exceptions;

import org.springframework.http.HttpStatus;

public class UserNameInvalidException extends BaseECommersException{

	private static final long serialVersionUID = -282122844754188988L;

	public UserNameInvalidException() {
		super (HttpStatus.BAD_REQUEST, "USER_USERNAME_INVALID", "User username must be a valid email");
	}

}
