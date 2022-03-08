package com.nttdata.lagm.credit.model;

import lombok.Data;

@Data
public abstract class BankProduct {
	private Long id;
	private String accountNumber;
	private String cci;
	private Long customerId;
}