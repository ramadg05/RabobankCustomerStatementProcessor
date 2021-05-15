package com.rabobank.model;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Rama Devi G
 *
 */

@NoArgsConstructor
@Data
public class CustomerStatementResponse {
	
	private String result;
	private List<CustomerStatementRecord> errorRecords;
	
}
