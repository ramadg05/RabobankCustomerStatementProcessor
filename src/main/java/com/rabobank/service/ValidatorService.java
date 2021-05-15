package com.rabobank.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.rabobank.model.CustomerStatementRecord;

/**
 * @author Rama Devi G
 *
 */
@Service
public interface ValidatorService {

	public List<CustomerStatementRecord> getDuplicateTransactionRefRecords(List<CustomerStatementRecord> customerStatementRecords);
	public List<CustomerStatementRecord> getIncorrectEndBalanceRecords(List<CustomerStatementRecord> customerStatementRecords);
}
