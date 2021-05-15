package com.rabobank.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import com.rabobank.constants.CustomerStatementConstants;
import com.rabobank.model.CustomerStatementResponse;

@ExtendWith(MockitoExtension.class)
public class CustomResponsEntityExceptionHandlerTest {

	@InjectMocks
	CustomResponseEntityExceptionHandler handler;


	@Test public void testHandleParseException() {

		CustomerStatementResponse response =
				handler.handleParseException(new Exception());
		assertEquals(CustomerStatementConstants.BAD_REQUEST,
				response.getResult());
	}

	@Test public void testHandleAllException() {

		CustomerStatementResponse response =
				handler.handleAllExceptions(new Exception());
		assertEquals(CustomerStatementConstants.INTERNAL_SERVER_ERROR,
				response.getResult());
	}
}
