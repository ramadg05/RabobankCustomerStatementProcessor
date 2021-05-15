package com.rabobank.controller;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabobank.contoller.CustomerStatementProcessController;
import com.rabobank.model.CustomerStatementRecord;
import com.rabobank.service.ValidatorService;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(CustomerStatementProcessController.class)
class CustomerStatementProcessControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private ValidatorService validatorService;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	void customerStatementNoDuplicateAndNoIncorrectEndBalanceTest() throws Exception {


		CustomerStatementRecord customerStatementRecord = new CustomerStatementRecord();
		CustomerStatementRecord customerStatementRecord1 = new CustomerStatementRecord();
		List<CustomerStatementRecord> customerStatementRecordList =  new ArrayList<CustomerStatementRecord>();
		customerStatementRecord.setAccountNumber("NL90RABO0979679769");
		customerStatementRecord1.setAccountNumber("NL90RABO0087001234");
		customerStatementRecordList.add(customerStatementRecord);
		customerStatementRecordList.add(customerStatementRecord1);
		
		List<CustomerStatementRecord> customerStatementDuplicateRecordList = new ArrayList<CustomerStatementRecord>();
		List<CustomerStatementRecord> customerStatementIncorrectRecordList = new ArrayList<CustomerStatementRecord>();
		
		when(validatorService.getDuplicateTransactionRefRecords(customerStatementDuplicateRecordList))
			.thenReturn(new ArrayList<CustomerStatementRecord>());
		when(validatorService.getIncorrectEndBalanceRecords(customerStatementIncorrectRecordList))
			.thenReturn(new ArrayList<CustomerStatementRecord>());
		 
		mockMvc
		.perform(post("/api/v1/rabobank/process")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(customerStatementRecordList)))
		.andExpect(jsonPath("$.result", is("SUCCESSFUL")));
		
	}
	

	@Test
	void customerStatementDuplicateAndNoIncorrectEndBalanceTest() throws Exception {


		CustomerStatementRecord customerStatementRecord = new CustomerStatementRecord();
		CustomerStatementRecord customerStatementRecord1 = new CustomerStatementRecord();
		List<CustomerStatementRecord> customerStatementRecordList =  new ArrayList<CustomerStatementRecord>();
		customerStatementRecord.setTransactionReference(123);
		customerStatementRecord1.setTransactionReference(123);
		customerStatementRecordList.add(customerStatementRecord);
		customerStatementRecordList.add(customerStatementRecord1);
		
		List<CustomerStatementRecord> customerStatementDuplicateRecordList = new ArrayList<CustomerStatementRecord>();
		CustomerStatementRecord customerStatementDuplicateRecord = new CustomerStatementRecord();
		customerStatementRecord.setTransactionReference(123);
		customerStatementDuplicateRecordList.add(customerStatementDuplicateRecord);
		
		List<CustomerStatementRecord> customerStatementIncorrectRecordList = new ArrayList<CustomerStatementRecord>();
		
		when(validatorService.getDuplicateTransactionRefRecords(customerStatementRecordList))
			.thenReturn(customerStatementDuplicateRecordList);
		when(validatorService.getIncorrectEndBalanceRecords(customerStatementIncorrectRecordList))
			.thenReturn(new ArrayList<CustomerStatementRecord>());
		 
		mockMvc
		.perform(post("/api/v1/rabobank/process")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(customerStatementRecordList)))
		.andExpect(jsonPath("$.result", is("DUPLICATE_REFERENCE")));
		
	}
	
	@Test
	void customerStatementNoDuplicateAndIncorrectEndBalanceTest() throws Exception {


		CustomerStatementRecord customerStatementRecord = new CustomerStatementRecord();
		List<CustomerStatementRecord> customerStatementRecordList =  new ArrayList<CustomerStatementRecord>();
		customerStatementRecord.setTransactionReference(123);
		customerStatementRecordList.add(customerStatementRecord);
		
		List<CustomerStatementRecord> customerStatementIncorrectRecordList = new ArrayList<CustomerStatementRecord>();
		CustomerStatementRecord customerStatementIncorrectRecord = new CustomerStatementRecord();
		customerStatementIncorrectRecord.setTransactionReference(123);
		customerStatementIncorrectRecord.setStartBalance(10.12);
		customerStatementIncorrectRecord.setMutation(10.12);
		customerStatementIncorrectRecord.setEndBalance(10.12);
		customerStatementIncorrectRecordList.add(customerStatementIncorrectRecord);
		
		List<CustomerStatementRecord> customerStatementDuplicateRecordList = new ArrayList<CustomerStatementRecord>();
		
		when(validatorService.getDuplicateTransactionRefRecords(customerStatementRecordList))
			.thenReturn(customerStatementDuplicateRecordList);
		when(validatorService.getIncorrectEndBalanceRecords(customerStatementRecordList))
			.thenReturn(customerStatementIncorrectRecordList);
		 
		mockMvc
		.perform(post("/api/v1/rabobank/process")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(customerStatementRecordList)))
		.andExpect(jsonPath("$.result", is("INCORRECT_END_BALANCE")));
	}
	
	@Test
	void customerStatementDuplicateAndIncorrectEndBalanceTest() throws Exception {


		CustomerStatementRecord customerStatementRecord = new CustomerStatementRecord();
		CustomerStatementRecord customerStatementRecord1 = new CustomerStatementRecord();
		List<CustomerStatementRecord> customerStatementRecordList =  new ArrayList<CustomerStatementRecord>();
		customerStatementRecord.setTransactionReference(123);
		customerStatementRecord1.setTransactionReference(123);
		customerStatementRecordList.add(customerStatementRecord);
		customerStatementRecordList.add(customerStatementRecord1);
		
		List<CustomerStatementRecord> customerStatementIncorrectRecordList = new ArrayList<CustomerStatementRecord>();
		CustomerStatementRecord customerStatementIncorrectRecord = new CustomerStatementRecord();
		customerStatementIncorrectRecord.setTransactionReference(123);
		customerStatementIncorrectRecord.setStartBalance(10.12);
		customerStatementIncorrectRecord.setMutation(10.12);
		customerStatementIncorrectRecord.setEndBalance(10.12);
		customerStatementIncorrectRecordList.add(customerStatementIncorrectRecord);
		
		List<CustomerStatementRecord> customerStatementDuplicateRecordList = new ArrayList<CustomerStatementRecord>();
		CustomerStatementRecord customerStatementDuplicateRecord = new CustomerStatementRecord();
		customerStatementRecord.setTransactionReference(123);
		customerStatementDuplicateRecordList.add(customerStatementDuplicateRecord);
		
		when(validatorService.getDuplicateTransactionRefRecords(customerStatementRecordList))
			.thenReturn(customerStatementDuplicateRecordList);
		when(validatorService.getIncorrectEndBalanceRecords(customerStatementRecordList))
			.thenReturn(customerStatementIncorrectRecordList);
		 
		mockMvc
		.perform(post("/api/v1/rabobank/process")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(customerStatementRecordList)))
		.andExpect(jsonPath("$.result", is("DUPLICATE_REFERENCE_INCORRECT_END_BALANCE")));
		
	}
	
	
	@Test
	void processCustomerStatemenNotFoundTest() throws Exception {


		CustomerStatementRecord customerStatementRecord = new CustomerStatementRecord();
		CustomerStatementRecord customerStatementRecord1 = new CustomerStatementRecord();
		List<CustomerStatementRecord> customerStatementRecordList =  new ArrayList<CustomerStatementRecord>();
		customerStatementRecord.setAccountNumber("NL90RABO0979679769");
		customerStatementRecord1.setAccountNumber("NL90RABO0087001234");
		customerStatementRecordList.add(customerStatementRecord);
		customerStatementRecordList.add(customerStatementRecord1);
		 
		mockMvc.perform(post("/api/v1/rabobank/process1") 
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(customerStatementRecordList)))
		          .andExpect(status().isNotFound());
	}
	
}
