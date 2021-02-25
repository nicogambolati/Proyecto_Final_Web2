package com.ecommers.models;

import java.util.Date;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class Report {
	
	private @Id @GeneratedValue Long id;
	private Date processedDateTime = new Date (System.currentTimeMillis());
	private Double profit;
	private Integer totalCartsFailed;
	private Integer totalCartsProcessed;
	private @OneToMany (mappedBy = "report") @JsonBackReference Set<ReportProduct> products;
	
	public Set<ReportProduct> getProducts() {
		return products;
	}
	public void setProducts(Set<ReportProduct> products) {
		this.products = products;
	}
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
	
}
