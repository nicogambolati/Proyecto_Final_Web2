package com.ecommers.exceptions;

import org.springframework.http.HttpStatus;

public class UserNotPresentException extends BaseECommersException{
	private static final long serialVersionUID = -975011837589523001L;

	public UserNotPresentException() {
		super (HttpStatus.NOT_FOUND, "USER_NOT_PRESENT", "User not present in repository");
	}

}
