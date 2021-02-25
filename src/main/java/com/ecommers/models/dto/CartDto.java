package com.ecommers.models.dto;

import java.util.Date;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;

public class CartDto {
	private Long id;
	private String email;
	private String fullName;
	@JsonBackReference
	private Set<ProductInCartDto> products;
	private Date creationDate = new Date (System.currentTimeMillis());
	private String status = "NEW";
	private Double total = 0.0;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Double getTotal() {
		return total;
	}
	public void setTotal(Double total) {
		this.total = total;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	public Set<ProductInCartDto> getProducts() {
		return products;
	}
	public void setProducts(Set<ProductInCartDto> products) {
		this.products = products;
	}
}
