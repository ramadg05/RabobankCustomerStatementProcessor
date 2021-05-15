package com.rabobank.controller;

import static org.hamcrest.Matchers.is;
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
public class CustomerStatementProcessControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private ValidatorService validatorService;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	void processCustomerStatementTest() throws Exception {


		CustomerStatementRecord customerStatementRecord = new CustomerStatementRecord();
		CustomerStatementRecord customerStatementRecord1 = new CustomerStatementRecord();
		List<CustomerStatementRecord> customerStatementRecordList =  new ArrayList<CustomerStatementRecord>();
		customerStatementRecord.setAccountNumber("NL91RABO0315273637");
		customerStatementRecord1.setAccountNumber("NL91RABO0315278769");
		customerStatementRecordList.add(customerStatementRecord);
		customerStatementRecordList.add(customerStatementRecord1);
		 
		mockMvc
		.perform(post("/api/v1/rabobank/process")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(customerStatementRecordList)))
		.andExpect(jsonPath("$.result", is("SUCCESSFUL")));
	}
	
	@Test
	void processCustomerStatemenNotFoundTest() throws Exception {


		CustomerStatementRecord customerStatementRecord = new CustomerStatementRecord();
		CustomerStatementRecord customerStatementRecord1 = new CustomerStatementRecord();
		List<CustomerStatementRecord> customerStatementRecordList =  new ArrayList<CustomerStatementRecord>();
		customerStatementRecord.setAccountNumber("NL91RABO0315273637");
		customerStatementRecord1.setAccountNumber("NL91RABO0315278769");
		customerStatementRecordList.add(customerStatementRecord);
		customerStatementRecordList.add(customerStatementRecord1);
		 
		mockMvc.perform(post("/api/v1/rabobank/process1") 
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(customerStatementRecordList)))
		          .andExpect(status().isNotFound());
	}
	
}
