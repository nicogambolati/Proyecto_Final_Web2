package com.ecommers.exceptions;

import org.springframework.http.HttpStatus;

public class ProductUnitPriceInvalidException extends BaseECommersException {
	private static final long serialVersionUID = -8467513424654290286L;

	public ProductUnitPriceInvalidException() {
		super (HttpStatus.BAD_REQUEST, "PRODUCT_UNITPRICE_INVALID", "Product unitprice must be greater that 0");
	}

}
