package com.ecommers.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommers.models.ProductInCart;

@Repository
public interface IProductInCartRepository extends JpaRepository<ProductInCart, Long>{
	ProductInCart findByProductId(Long productId);
}
