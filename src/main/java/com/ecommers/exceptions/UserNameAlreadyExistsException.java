package com.ecommers.exceptions;

import org.springframework.http.HttpStatus;

public class UserNameAlreadyExistsException extends BaseECommersException {

	private static final long serialVersionUID = -6505575648409464675L;

	public UserNameAlreadyExistsException() {
		super (HttpStatus.CONFLICT, "USER_USERNAME_ALREADY_EXISTS", "User username already exist");
	}

}
