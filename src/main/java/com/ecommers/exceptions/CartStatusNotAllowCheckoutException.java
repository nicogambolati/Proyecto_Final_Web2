package com.ecommers.exceptions;

import org.springframework.http.HttpStatus;

public class CartStatusNotAllowCheckoutException extends BaseECommersException{
	private static final long serialVersionUID = 3353756761111495593L;

	public CartStatusNotAllowCheckoutException() {
		super (HttpStatus.CONFLICT, "CART_STATUS_NOT_ALLOW_CHECKOUT", "Cart status not allow checkout");
	}

}
