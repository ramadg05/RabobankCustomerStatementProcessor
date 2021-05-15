package com.rabobank.exception;

import java.util.ArrayList;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.rabobank.constants.CustomerStatementConstants;
import com.rabobank.model.CustomerStatementRecord;
import com.rabobank.model.CustomerStatementResponse;

@RestControllerAdvice  
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {


	@Override
	protected final ResponseEntity<Object> handleMethodArgumentNotValid(
			MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		CustomerStatementResponse errorDetails = new CustomerStatementResponse();
		errorDetails.setResult(CustomerStatementConstants.BAD_REQUEST);
		return new ResponseEntity<>(errorDetails, status); 
	}
	
	@ExceptionHandler(Exception.class) 
	public final CustomerStatementResponse handleAllExceptions(Exception ex) { 
		CustomerStatementResponse errorDetails = new CustomerStatementResponse();
		errorDetails.setResult(CustomerStatementConstants.INTERNAL_SERVER_ERROR);
		errorDetails.setErrorRecords(new ArrayList<CustomerStatementRecord>());
		return errorDetails; 
	}

}
 