package com.ecommers.exceptions;

import org.springframework.http.HttpStatus;

public class UserIsBlockedException extends BaseECommersException {

	private static final long serialVersionUID = -3989823367003919145L;

	public UserIsBlockedException() {
		super (HttpStatus.CONFLICT, "USER_IS_BLOCKED", "User blocked");
	}

}
