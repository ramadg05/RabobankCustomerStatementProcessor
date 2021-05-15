package com.rabobank.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Rama Devi G
 *
 */
@NoArgsConstructor
@Data
public class CustomerStatementRecord {

	private int transactionReference;
	private String accountNumber;
	private double startBalance;
	private double mutation;
	private String description;
	private double endBalance;

	public CustomerStatementRecord(int transactionReference, String accountNumber, double startBalance, double mutation,  String description, 
			double endBalance) {
		super();
		this.transactionReference = transactionReference;
		this.accountNumber = accountNumber;
		this.startBalance = startBalance;
		this.mutation = mutation;
		this.description = description;
		this.endBalance = endBalance;
	}
}
