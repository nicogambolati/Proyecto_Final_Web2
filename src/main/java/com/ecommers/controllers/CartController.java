package com.ecommers.controllers;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ecommers.models.dto.CartDto;
import com.ecommers.models.dto.CartProductDto;
import com.ecommers.models.dto.ProductInCartDto;
import com.ecommers.service.Interface.ICartService;

@RestController
public class CartController {

	private final ICartService cartService;

	public CartController(ICartService cartService) {
		super();
		this.cartService = cartService;
	}

	@PostMapping(value = "/carts")
	public ResponseEntity<CartDto> createCart(/* @Valid */ @RequestBody CartDto cart_dto) {
		CartDto existingCart = this.cartService.GetExisitingCart(cart_dto);
		if (existingCart != null) {
			return new ResponseEntity<>(existingCart, HttpStatus.OK);
		}		
		
		CartDto result = this.cartService.create(cart_dto);
		return new ResponseEntity<>(result, HttpStatus.CREATED);
	}

	@PostMapping(value = "/carts/{id}/products")
	public ResponseEntity<CartDto> updateCart(@PathVariable long id,
			@RequestBody CartProductDto cardProductDto) {
		CartDto result = this.cartService.addProducts(id, cardProductDto);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@GetMapping(value = "/carts/{id}")
	public ResponseEntity<CartDto> getCard(@PathVariable long id) {
		CartDto cartDto = this.cartService.get(id);
		return new ResponseEntity<>(cartDto, HttpStatus.OK);
	}

	@DeleteMapping(value = "/carts/{cartId}/products/{productId}")
	public ResponseEntity<ProductInCartDto> deleteProduct(@PathVariable long cartId, @PathVariable long productId) {
		ProductInCartDto result = this.cartService.DeleteProduct(cartId, productId);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@GetMapping(value = "/carts/{cartId}/products")
	public ResponseEntity<Set<ProductInCartDto>> GetProductsInCart(@PathVariable long cartId) {
		Set<ProductInCartDto> result = this.cartService.GetCartProducts(cartId);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@PostMapping(value = "/carts/{id}/checkout")
	public ResponseEntity<CartDto> updateCart(@PathVariable long id) {
		CartDto result = this.cartService.CheckoutCart(id);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@GetMapping(value = {"/carts/status", "/carts/status/{status}"})
	public ResponseEntity <List <CartDto> > getAllCartStatus (@PathVariable (required=false) Optional <String> status){
		List <CartDto> result = this.cartService.searhByStatus(status);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
}
