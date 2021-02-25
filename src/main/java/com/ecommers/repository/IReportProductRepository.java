package com.ecommers.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommers.models.ReportProduct;

public interface IReportProductRepository extends JpaRepository<ReportProduct, Long> {

}
