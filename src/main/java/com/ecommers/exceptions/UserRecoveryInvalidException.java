package com.ecommers.exceptions;

import org.springframework.http.HttpStatus;

public class UserRecoveryInvalidException extends BaseECommersException {

	private static final long serialVersionUID = -5495306124018469853L;

	public UserRecoveryInvalidException() {
		super (HttpStatus.BAD_REQUEST, "USER_RECOVERY_INVALID", "Invalid data for user recovery");
	}

}
