
package com.rabobank.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import com.rabobank.model.CustomerStatementRecord;

@ExtendWith(MockitoExtension.class)
class ValidatorServiceImplTest {

	@InjectMocks
	private ValidatorServiceImpl validatorServiceImpl;

	@Test
	void testGetDuplicateRecords() {
		List<CustomerStatementRecord> customerStatementRecords = new ArrayList<>();

		// Record List with two duplicate records
		CustomerStatementRecord record1 = new CustomerStatementRecord(894261, "NL90RABO0979679770", 10.5, 20.5,
				"Record1", 31.0);
		CustomerStatementRecord record2 = new CustomerStatementRecord(894262, "NL90RABO0979679769", 10.5, 20.5,
				"Record2", 10.5);
		CustomerStatementRecord record3 = new CustomerStatementRecord(894261, "NL90RABO0979679768", 10.5, -20.5,
				"Record3", 15.0);
		customerStatementRecords.add(record1);
		customerStatementRecords.add(record2);
		customerStatementRecords.add(record3);
		List<CustomerStatementRecord> duplicateRecords = validatorServiceImpl
				.getDuplicateTransactionRefRecords(customerStatementRecords);
		

		assertNotNull(duplicateRecords);
		assertEquals(duplicateRecords.size(), 2);
	}

	@Test
	void testIncorrectEndBalanceErrorRecords() {
		List<CustomerStatementRecord> customerStatementRecords = new ArrayList<>();

		// Record List with two End balance error records
		CustomerStatementRecord record1 = new CustomerStatementRecord(894261, "NL90RABO0979679770", 10.5, 20.5,
				"Record1", 31.0);
		CustomerStatementRecord record2 = new CustomerStatementRecord(894262, "NL90RABO0979679769", 10.5, 20.5,
				"Record2", 10.5);
		CustomerStatementRecord record3 = new CustomerStatementRecord(894263, "NL90RABO0979679768", 10.5, -20.5,
				"Record3", 15.0);
		customerStatementRecords.add(record1);
		customerStatementRecords.add(record2);
		customerStatementRecords.add(record3);
		
		List<CustomerStatementRecord> incorrectEndBalanceRecords = validatorServiceImpl
				.getIncorrectEndBalanceRecords(customerStatementRecords);

		assertNotNull(incorrectEndBalanceRecords);
		assertEquals(incorrectEndBalanceRecords.size(), 2);
	}

}
