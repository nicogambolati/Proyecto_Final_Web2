package com.ecommers.exceptions;

import org.springframework.http.HttpStatus;

public class UserNameRequiredException extends BaseECommersException{
	private static final long serialVersionUID = 4115686283976828697L;

	public UserNameRequiredException() {
		super(HttpStatus.BAD_REQUEST, "USER_USERNAME_REQUIRED", "User username is required");
	}

}
