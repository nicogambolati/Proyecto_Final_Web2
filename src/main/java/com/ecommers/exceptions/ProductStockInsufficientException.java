package com.ecommers.exceptions;

import org.springframework.http.HttpStatus;

public class ProductStockInsufficientException extends BaseECommersException{
	private static final long serialVersionUID = -7031776336434873905L;

	public ProductStockInsufficientException() {
		super (HttpStatus.CONFLICT, "PRODUCT_STOCK_INSUFFICIENT", "Product stock insufficient");
	}

}
