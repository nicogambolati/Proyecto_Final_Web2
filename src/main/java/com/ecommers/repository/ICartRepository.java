package com.ecommers.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommers.models.Cart;

@Repository
public interface ICartRepository extends JpaRepository<Cart, Long> {
	
	List <Cart> findAllByStatus(String Status);
	List<Cart> findAllByEmailAndStatus (String email, String status);
	List<Cart> findAllByStatusAndCreationDateBetween(String Status, Date from, Date to);
}
