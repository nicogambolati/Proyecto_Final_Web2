package com.ecommers.service.Interface;

import java.util.List;

import com.ecommers.models.dto.CartDto;
import com.ecommers.models.dto.ReportDto;

public interface IBatchService {

	ReportDto processedReadyCarts();
	List<CartDto> getCartsProcessed(String from, String to);
	List<CartDto> getCartsProcessed();
}
