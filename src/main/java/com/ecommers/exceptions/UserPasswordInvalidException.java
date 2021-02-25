package com.ecommers.exceptions;

import org.springframework.http.HttpStatus;

public class UserPasswordInvalidException extends BaseECommersException {
	private static final long serialVersionUID = -322406039194573248L;

	public UserPasswordInvalidException() {
		super (HttpStatus.BAD_REQUEST, "USER_PASSWORD_INVALID", "User password must be a valid:" + 
															"Minimum length 8, Maximum" + 
															"length 32. At least 1 char, At least " + 
															"1 number. Not allowed special " + 
															"characters, only (- _ .)");
															
	}

}
