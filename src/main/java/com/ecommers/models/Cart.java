package com.ecommers.models;

import java.util.Date;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class Cart {
	private @Id @GeneratedValue Long id;
	private String email;
	private String fullName;
	private @OneToMany (mappedBy = "cart") @JsonBackReference Set<ProductInCart> products;
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
	public Set<ProductInCart> getProducts() {
		return products;
	}
	public void setProducts(Set<ProductInCart> products) {
		this.products = products;
	}
	
	public Double calculateTotal() {
		Double total = 0.0;
		for (ProductInCart _product : products ) {
			total += _product.getUnitPrice() * _product.getQuantity();
		}
		return total;
	}

}
