package com.ecommers.exceptions;

import org.springframework.http.HttpStatus;

public class CartProcessingNotAllowDeletionException extends BaseECommersException {
	private static final long serialVersionUID = 8921697935772995214L;

	public CartProcessingNotAllowDeletionException() {
		super (HttpStatus.CONFLICT, "CART_PROCESSING_NOT_ALLOW_DELETION", "Cart processing. Deletion is not possible");
	}
	
}
