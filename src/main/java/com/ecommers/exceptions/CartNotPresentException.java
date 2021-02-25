package com.ecommers.exceptions;

import org.springframework.http.HttpStatus;

public class CartNotPresentException extends BaseECommersException{
	private static final long serialVersionUID = -6062693391037221118L;

	public CartNotPresentException() {
		super (HttpStatus.NOT_FOUND, "CART_NOT_PRESENT", "Cart not present in repository");
	}

}
