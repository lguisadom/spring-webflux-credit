package com.nttdata.lagm.credit.dto.request;

import lombok.Data;

@Data
public class CreditRequestDto {
	private String accountNumber;
	private String cci;
	private String customerId;
	private String amount;
	private String creditLimit;
}
