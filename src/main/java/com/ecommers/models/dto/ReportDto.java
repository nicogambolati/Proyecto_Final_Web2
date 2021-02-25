package com.ecommers.models.dto;

import java.util.Date;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;

public class ReportDto {

	private Long id;
	private Date processedDateTime;
	private Double profit;
	private Integer totalCartsFailed;
	private Integer totalCartsProcessed;
	// @JsonBackReference
	private Set<ProductInCartDto> products;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getProcessedDateTime() {
		return processedDateTime;
	}
	public void setProcessedDateTime(Date processedDateTime) {
		this.processedDateTime = processedDateTime;
	}
	public Double getProfit() {
		return profit;
	}
	public void setProfit(Double profit) {
		this.profit = profit;
	}
	public Integer getTotalCartsFailed() {
		return totalCartsFailed;
	}
	public void setTotalCartsFailed(Integer totalCartsFailed) {
		this.totalCartsFailed = totalCartsFailed;
	}
	public Integer getTotalCartsProcessed() {
		return totalCartsProcessed;
	}
	public void setTotalCartsProcessed(Integer totalCartsProcessed) {
		this.totalCartsProcessed = totalCartsProcessed;
	}
	public Set<ProductInCartDto> getProducts() {
		return products;
	}
	public void setProducts(Set<ProductInCartDto> products) {
		this.products = products;
	}	
}
