package com.rabobank.exception;

import java.io.IOException;
import java.util.ArrayList;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.rabobank.constants.CustomerStatementConstants;
import com.rabobank.model.CustomerStatementRecord;
import com.rabobank.model.CustomerStatementResponse;

@RestControllerAdvice 
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {


	@ExceptionHandler({JsonMappingException.class, JsonParseException.class, IOException.class})
	public final CustomerStatementResponse handleParseException(Exception ex) {
		CustomerStatementResponse errorDetails = new CustomerStatementResponse();
		errorDetails.setResult(CustomerStatementConstants.BAD_REQUEST);
		errorDetails.setErrorRecords(new ArrayList<CustomerStatementRecord>());
		return errorDetails; 
	}

	@ExceptionHandler(Exception.class) 
	public final CustomerStatementResponse handleAllExceptions(Exception ex) { 
		CustomerStatementResponse errorDetails = new CustomerStatementResponse();
		errorDetails.setResult(CustomerStatementConstants.INTERNAL_SERVER_ERROR);
		errorDetails.setErrorRecords(new ArrayList<CustomerStatementRecord>());
		return errorDetails; 
	}

}
 