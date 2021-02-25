package com.ecommers.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommers.models.Product;

@Repository
public interface IProductRepository extends JpaRepository <Product, Long>{

}
