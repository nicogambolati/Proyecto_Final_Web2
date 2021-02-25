package com.ecommers.service.Interface;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.ecommers.models.dto.CartDto;
import com.ecommers.models.dto.CartProductDto;
import com.ecommers.models.dto.ProductInCartDto;

public interface ICartService {
	 CartDto create (CartDto cart);
	 
	 CartDto get (long id);

	 CartDto addProducts(long id, CartProductDto CartProduct);

	 ProductInCartDto DeleteProduct(long id, long productId);

	Set<ProductInCartDto> GetCartProducts(long id);

	CartDto CheckoutCart(long id);

	List<CartDto> searhByStatus(Optional<String> status);

	CartDto GetExisitingCart(CartDto cart_dto);
}
