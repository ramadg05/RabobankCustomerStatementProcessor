package com.rabobank.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.WebRequest;

import com.rabobank.constants.CustomerStatementConstants;
import com.rabobank.model.CustomerStatementResponse;

@ExtendWith(MockitoExtension.class)
class CustomResponsEntityExceptionHandlerTest {

	@InjectMocks
	CustomResponseEntityExceptionHandler handler;

	@Mock
	MethodArgumentNotValidException exception;

	@Mock
	HttpHeaders headers;

	@Mock
	WebRequest request;

	@Test
	void testHandleParseException() {

		ResponseEntity<Object> response = handler.handleMethodArgumentNotValid(exception, headers,
				HttpStatus.BAD_REQUEST, request);
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}

	@Test
	void testHandleAllException() {

		CustomerStatementResponse response = handler.handleAllExceptions(new Exception());
		assertEquals(CustomerStatementConstants.INTERNAL_SERVER_ERROR, response.getResult());
	}
}
