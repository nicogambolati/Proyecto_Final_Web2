package com.ecommers.exceptions;

import org.springframework.http.HttpStatus;

public class ProductIdNotRequiredException extends BaseECommersException {
	private static final long serialVersionUID = 2847576973986489336L;

	public ProductIdNotRequiredException() {
		super (HttpStatus.BAD_REQUEST, "PRODUCT_ID_NOT_REQUIRED", "Product id is not required");
	}

}
