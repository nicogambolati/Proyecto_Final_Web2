package com.ecommers.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.ecommers.models.ErrorAPI;

@ControllerAdvice
public class ProductServiceErrorAdvice {
	@ExceptionHandler({RuntimeException.class})
    public ResponseEntity<ErrorAPI> handleRunTimeException(RuntimeException e) {
        return error(HttpStatus.INTERNAL_SERVER_ERROR, "RUNTIME", e.getMessage());
    }
	
	private ResponseEntity<ErrorAPI> error(HttpStatus status, String errorCode, String errorMessage) {
    	//logger.error("Exception : " + status + " - " + errorCode);
        return ResponseEntity.status(status).body(new ErrorAPI(errorCode, errorMessage));
    }
	
	@ExceptionHandler({BaseECommersException.class})
    public ResponseEntity<ErrorAPI> handleBaseECommersException(BaseECommersException e) {
        return error(e.getStatusCode(), e.getErrorCode(), e.getMessage());
    }
}
