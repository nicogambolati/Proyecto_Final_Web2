package com.ecommers.exceptions;

import org.springframework.http.HttpStatus;

public class ProductDescriptionRequiredException extends BaseECommersException {
	private static final long serialVersionUID = 2323555975947760932L;

	public ProductDescriptionRequiredException () {
		super (HttpStatus.BAD_REQUEST, "PRODUCT_DESCRIPTION_REQUIRED", "Product description is required");
	}

}
