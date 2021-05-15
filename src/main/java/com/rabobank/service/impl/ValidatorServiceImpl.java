package com.rabobank.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.rabobank.model.CustomerStatementRecord;
import com.rabobank.service.ValidatorService;

/**
 * @author Rama Devi G
 *
 */
@Service
public class ValidatorServiceImpl implements ValidatorService {

	@Override
	public List<CustomerStatementRecord> getDuplicateTransactionRefRecords(List<CustomerStatementRecord> customerStatementList) {

		return customerStatementList.stream()
				  .collect(Collectors.groupingBy(CustomerStatementRecord::getTransactionReference))
				  .entrySet().stream()
				  .filter(e -> e.getValue().size() > 1)
				  .flatMap(e -> e.getValue().stream())
				  .collect(Collectors.toList());
	}

	@Override
	public List<CustomerStatementRecord> getIncorrectEndBalanceRecords(List<CustomerStatementRecord> customerStatementList) {
		List<CustomerStatementRecord> incorretEndBalanceRecords = new ArrayList<>();
		
		customerStatementList.forEach(customerStatement -> {
			double balance = customerStatement.getStartBalance() + customerStatement.getMutation() - customerStatement.getEndBalance();
			BigDecimal resultRounded = BigDecimal.valueOf(balance).setScale(2, BigDecimal.ROUND_HALF_UP);
			if (resultRounded.doubleValue() != 0.0) {
				incorretEndBalanceRecords.add(customerStatement);
			}
		});
		return incorretEndBalanceRecords;
	}

}
