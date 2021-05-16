package com.rabobank;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import com.rabobank.model.CustomerStatementRecord;

@SpringBootTest(classes = CustomerStatementProcessorApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CustomerStatementProcessorApplicationTests {

	@LocalServerPort
	private int port;

	TestRestTemplate restTemplate = new TestRestTemplate();

	HttpHeaders headers = new HttpHeaders();

	private String createURLWithPort(String uri) {
		return "http://localhost:" + port + uri;
	}

	@Test
	void customerStatementNoDuplicateAndNoIncorrectEndBalanceIntegrationTest() throws Exception {

		CustomerStatementRecord customerStatementRecord = new CustomerStatementRecord();
		CustomerStatementRecord customerStatementRecord1 = new CustomerStatementRecord();
		List<CustomerStatementRecord> customerStatementRecordList = new ArrayList<CustomerStatementRecord>();
		customerStatementRecord.setTransactionReference(123);
		customerStatementRecord1.setTransactionReference(1234);
		customerStatementRecordList.add(customerStatementRecord);
		customerStatementRecordList.add(customerStatementRecord1);

		HttpEntity<List<CustomerStatementRecord>> entity = new HttpEntity<List<CustomerStatementRecord>>(
				customerStatementRecordList, headers);

		ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("/api/v1/rabobank/process"),
				HttpMethod.POST, entity, String.class);

		assertNotNull(response.getBody());
		assertTrue(response.getBody().contains("SUCCESSFUL"));

	}

}
