package com.ecommers.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.ecommers.exceptions.ProductDescriptionRequiredException;
import com.ecommers.exceptions.ProductIdNotRequiredException;
import com.ecommers.exceptions.ProductIdRequiredException;
import com.ecommers.exceptions.ProductNotPresentException;
import com.ecommers.exceptions.ProductStockInvalidException;
import com.ecommers.exceptions.ProductStockRequiredException;
import com.ecommers.exceptions.ProductUnitPriceInvalidException;
import com.ecommers.exceptions.ProductUnitPriceRequiredException;
import com.ecommers.models.Product;
import com.ecommers.models.dto.ProductDto;
import com.ecommers.repository.IProductRepository;
import com.ecommers.service.Interface.IProductService;

@Service
public class ProductService implements IProductService {
	private final IProductRepository productRepository;
	
	public ProductService(IProductRepository productRepository) {
		super();
		this.productRepository = productRepository;
	}

	@Override
	public List<ProductDto> getAll() {
		List<Product> products = this.productRepository.findAll();
		List<ProductDto> dtos = new ArrayList<>(products.size());
		
		for (int i = 0; i < products.size(); i++) {
			ProductDto productDto = new ProductDto();
			BeanUtils.copyProperties(products.get(i), productDto);
			dtos.add(productDto);
		}		
		return dtos;
	}
	
	@Override
	public ProductDto get(long id) {
		ProductDto productDto = new ProductDto();
		Optional<Product> product  = this.productRepository.findById(id);
		if (!product.isPresent()) {
			throw new ProductNotPresentException();
		}
		BeanUtils.copyProperties(product.get(), productDto);
		return productDto;
	}

	@Override
	public ProductDto Create(ProductDto product_dto) {
		if (product_dto.getId() != null) {
			throw new ProductIdNotRequiredException();
		}
		if (product_dto.getDescription() == null) {
			throw new ProductDescriptionRequiredException();
		}
		if (product_dto.getStock() == null) {
			throw new ProductStockRequiredException();
		}
		if (product_dto.getStock() < 0) {
			throw new ProductStockInvalidException();
		}
		if (product_dto.getUnitPrice() == null) {
			throw new ProductUnitPriceRequiredException();
		}
		if (product_dto.getUnitPrice() < 0 ) {
			throw new ProductUnitPriceInvalidException();
		}
		Product product = new Product();
		BeanUtils.copyProperties(product_dto, product);
		this.productRepository.save(product);
		product_dto.setId(product.getId());
		return product_dto;
	}

	@Override
	public ProductDto Update(Optional<Long> id, ProductDto product_dto) {
		if (!id.isPresent()) {
			throw new ProductIdRequiredException();	
		}
		product_dto.setId(id.get());
		Optional<Product> _product  = this.productRepository.findById(product_dto.getId());
		if (!_product.isPresent()) {
			throw new ProductNotPresentException(); 
		}
		if (product_dto.getId() == null) {
			throw new ProductIdRequiredException();
		}
		if (product_dto.getDescription() == null) {
			throw new ProductDescriptionRequiredException();
		}
		if (product_dto.getStock() == null) {
			throw new ProductStockRequiredException();
		}
		if (product_dto.getStock() < 0) {
			throw new ProductStockInvalidException();
		}
		if (product_dto.getUnitPrice() == null) {
			throw new ProductUnitPriceRequiredException();
		}
		if (product_dto.getUnitPrice() < 0 ) {
			throw new ProductUnitPriceInvalidException();
		}		
		Product product = new Product();
		BeanUtils.copyProperties(product_dto, product);
		this.productRepository.save(product);
		return product_dto;
	}

	@Override
	public ProductDto Delete(Optional<Long> id) {
		if (!id.isPresent()) {
			throw new ProductNotPresentException();
		}
		Optional<Product> _product  = this.productRepository.findById(id.get());
		if (!_product.isPresent()) {
			throw new ProductNotPresentException(); 
		}
		
		ProductDto productDto = new ProductDto();
		this.productRepository.deleteById(id.get());
		BeanUtils.copyProperties(_product.get(), productDto);
		return productDto;
	}	
}
