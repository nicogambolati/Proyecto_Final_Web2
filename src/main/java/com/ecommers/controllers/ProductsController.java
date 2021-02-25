package com.ecommers.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ecommers.models.dto.ProductDto;
import com.ecommers.service.Interface.IProductService;

@RestController
public class ProductsController {
	
	private final IProductService productService;
	
	public ProductsController(IProductService productService) {
		super();
		this.productService = productService;
	}

	@GetMapping(value = "/products")
	public ResponseEntity<List<ProductDto>> getProducts() {
		List<ProductDto> dto = this.productService.getAll();
		return new ResponseEntity<>(dto, HttpStatus.OK);
	}
	
	@GetMapping(value = "/products/{id}")
	public ResponseEntity <ProductDto> getProduct(@PathVariable long id) {
		ProductDto productDto = this.productService.get(id);
		return new ResponseEntity<>(productDto, HttpStatus.OK);
	}
	
	@PostMapping(value = "/products")
	public ResponseEntity<ProductDto> createProduct(/*@Valid*/ @RequestBody ProductDto productDto) {
		ProductDto result =	this.productService.Create(productDto);
		return new ResponseEntity<>(result, HttpStatus.CREATED);
	}
	
	@PutMapping(value = { "/products", "/products/{id}"})
	public ResponseEntity <ProductDto> updateProduct (@PathVariable(required = false) Optional<Long> id, @RequestBody ProductDto productDto){
		ProductDto result = this.productService.Update(id, productDto);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@DeleteMapping(value = { "/products", "/products/{id}"} )
	public ResponseEntity <ProductDto> deleteProduct (@PathVariable Optional<Long> id){
		ProductDto result = this.productService.Delete(id);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
}
