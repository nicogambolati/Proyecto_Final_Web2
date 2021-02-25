package com.ecommers.exceptions;

import org.springframework.http.HttpStatus;

public class ProductIdRequiredException extends BaseECommersException{
	private static final long serialVersionUID = 5025516969739672390L;

	public ProductIdRequiredException() {
		super(HttpStatus.BAD_REQUEST, "PRODUCT_ID_REQUIRED", "Product id is required");
	}

}
