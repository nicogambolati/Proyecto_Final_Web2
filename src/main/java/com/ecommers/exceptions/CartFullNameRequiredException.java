package com.ecommers.exceptions;

import org.springframework.http.HttpStatus;

public class CartFullNameRequiredException extends BaseECommersException {

	private static final long serialVersionUID = 2214521765280532318L;

	public CartFullNameRequiredException() {
		super (HttpStatus.BAD_REQUEST, "CART_FULLNAME_REQUIRED", "Cart fullName is required");
	}

}
