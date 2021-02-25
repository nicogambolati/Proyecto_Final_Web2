package com.ecommers.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommers.models.Report;

public interface IReportRepository extends JpaRepository<Report, Long> {
	 
	
}
