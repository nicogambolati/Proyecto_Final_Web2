package com.ecommers.exceptions;

import org.springframework.http.HttpStatus;

public class ProductQuantityRequiredException extends BaseECommersException {
	private static final long serialVersionUID = -1785190551860393730L;

	public ProductQuantityRequiredException() {
		super (HttpStatus.BAD_REQUEST, "PRODUCT_QUANTITY_REQUIRED", "Product quantity is required") ;
	}

}
