package com.ecommers.exceptions;

import org.springframework.http.HttpStatus;

public class ProductUnitPriceRequiredException extends BaseECommersException{
	private static final long serialVersionUID = 704236814848997682L;

	public ProductUnitPriceRequiredException() {
		super (HttpStatus.BAD_REQUEST, "PRODUCT_UNITPRICE_REQUIRED", "Product unitprice is required");
	}

}
