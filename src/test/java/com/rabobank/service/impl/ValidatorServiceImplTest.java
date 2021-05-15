
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
		CustomerStatementRecord record1 = new CustomerStatementRecord(123456, "NL91RABO0315273637", 21.6, -41.83,
				"Record1", -20.23);
		CustomerStatementRecord record2 = new CustomerStatementRecord(123455, "NL91RABO0315278769", 86.66, 44.5,
				"Record2", 131.16);
		CustomerStatementRecord record3 = new CustomerStatementRecord(123456, "NL91RABO0315279235", 90.83, -10.91,
				"Record3", 79.92);
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
		CustomerStatementRecord record1 = new CustomerStatementRecord(194261, "NL91RABO0315273637", 21.6, -41.83,
				"Record1", -10.23);
		CustomerStatementRecord record2 = new CustomerStatementRecord(194262, "NL91RABO0315278769", 86.66, 44.5,
				"Record2", 131.16);
		CustomerStatementRecord record3 = new CustomerStatementRecord(194261, "NL91RABO0315279235", 90.83, -10.91,
				"Record3", 99.92);
		customerStatementRecords.add(record1);
		customerStatementRecords.add(record2);
		customerStatementRecords.add(record3);
		
		List<CustomerStatementRecord> incorrectEndBalanceRecords = validatorServiceImpl
				.getIncorrectEndBalanceRecords(customerStatementRecords);

		assertNotNull(incorrectEndBalanceRecords);
		assertEquals(incorrectEndBalanceRecords.size(), 2);
	}

}
