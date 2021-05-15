package com.rabobank.contoller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rabobank.constants.CustomerStatementConstants;
import com.rabobank.model.CustomerStatementRecord;
import com.rabobank.model.CustomerStatementResponse;
import com.rabobank.service.ValidatorService;

/**
 * @author Rama Devi G
 *
 */
@RestController
@RequestMapping("/api/v1/rabobank")
public class CustomerStatementProcessController {

	@Autowired
	private ValidatorService validatorService;

	@PostMapping(value="/process")
	public CustomerStatementResponse processCustomerStatement(@RequestBody List<CustomerStatementRecord> customerStatementRecords) throws IOException {
		return buildResponse(customerStatementRecords);
	}

	private CustomerStatementResponse buildResponse(List<CustomerStatementRecord> statementList) {
		CustomerStatementResponse customerStatementResponse = new CustomerStatementResponse();
		ArrayList<CustomerStatementRecord> duplicateReferenceRecords = new ArrayList<>();
		ArrayList<CustomerStatementRecord> incorrectEndBalanceRecords = new ArrayList<>();

		duplicateReferenceRecords.addAll(validatorService.getDuplicateTransactionRefRecords(statementList));
		incorrectEndBalanceRecords.addAll(validatorService.getIncorrectEndBalanceRecords(statementList));

		if (duplicateReferenceRecords.isEmpty() && incorrectEndBalanceRecords.isEmpty()) {
			customerStatementResponse.setResult(CustomerStatementConstants.SUCCESSFUL);
			customerStatementResponse.setErrorRecords(duplicateReferenceRecords);
		} else if (!duplicateReferenceRecords.isEmpty() && incorrectEndBalanceRecords.isEmpty()){
			customerStatementResponse.setResult(CustomerStatementConstants.DUPLICATE_REFERENCE);
			customerStatementResponse.setErrorRecords(duplicateReferenceRecords);
		} else if (!incorrectEndBalanceRecords.isEmpty() && duplicateReferenceRecords.isEmpty()){
			customerStatementResponse.setResult(CustomerStatementConstants.INCORRECT_END_BALANCE);
			customerStatementResponse.setErrorRecords(incorrectEndBalanceRecords);
		} else if (!incorrectEndBalanceRecords.isEmpty() && !duplicateReferenceRecords.isEmpty()){
			customerStatementResponse.setResult(CustomerStatementConstants.DUPLICATE_REFERENCE_INCORRECT_END_BALANCE);
			duplicateReferenceRecords.addAll(incorrectEndBalanceRecords);
			customerStatementResponse.setErrorRecords(duplicateReferenceRecords);
		}
		return customerStatementResponse;

	}
}
