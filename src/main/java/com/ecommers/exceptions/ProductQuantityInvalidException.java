package com.ecommers.exceptions;

import org.springframework.http.HttpStatus;

public class ProductQuantityInvalidException extends BaseECommersException {

	private static final long serialVersionUID = -1333263750369618091L;

	public ProductQuantityInvalidException() {
		super (HttpStatus.BAD_REQUEST, "PRODUCT_QUANTITY_INVALID", "Product quantity must be greater than 0") ;
	}

}
