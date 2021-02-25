package com.ecommers.exceptions;

import org.springframework.http.HttpStatus;

public class CartStatusNotSupportedException extends BaseECommersException{
	private static final long serialVersionUID = -5020780152495617473L;

	public CartStatusNotSupportedException() {
		super (HttpStatus.BAD_REQUEST, "CART_STATUS_NOT_SUPPORTED", "Cart status not supported");
	}

}
