package com.ecommers.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecommers.models.dto.CartDto;
import com.ecommers.models.dto.ReportDto;
import com.ecommers.service.Interface.IBatchService;

@RestController
public class BatchController {

	private final IBatchService batchService;
	
	public BatchController(IBatchService batchService) {
		super();
		this.batchService = batchService;
	}
	
	
	@PostMapping(value = "/batch/processCarts")
	public ResponseEntity<ReportDto> updateCart() {
		
		ReportDto result = this.batchService.processedReadyCarts();
		return new ResponseEntity<>( result, HttpStatus.OK);
	}
	
	@GetMapping("/batch/processCarts")
	public ResponseEntity <List <CartDto> > getAllCartStatus (@RequestParam Map<String, String> allRequestParams) {				
		List<CartDto> result;
		if (allRequestParams.get("from") == null || allRequestParams.get("to") == null) {
			result = this.batchService.getCartsProcessed();
		} else {
			result = this.batchService.getCartsProcessed(allRequestParams.get("from").replace('T', ' '), allRequestParams.get("to").replace('T', ' '));
		}
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

}
