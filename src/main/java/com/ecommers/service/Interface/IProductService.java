package com.ecommers.service.Interface;

import java.util.List;
import java.util.Optional;

import com.ecommers.models.dto.ProductDto;

public interface IProductService {
	
	List <ProductDto> getAll ();
	
	ProductDto get (long id);
	
	ProductDto Create (ProductDto product_dto);
	
	ProductDto Update (Optional<Long> id, ProductDto product_dto);
	
	ProductDto Delete(Optional<Long> id);
	
}
