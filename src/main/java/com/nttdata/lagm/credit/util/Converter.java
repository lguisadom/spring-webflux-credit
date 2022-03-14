package com.nttdata.lagm.credit.util;

import com.nttdata.lagm.credit.dto.request.CreditRequestDto;
import com.nttdata.lagm.credit.model.Credit;
import com.nttdata.lagm.credit.model.Customer;

public class Converter {
	public static Credit convertoToCredit(CreditRequestDto creditRequestDto) {
		Credit credit = new Credit();
		credit.setAccountNumber(creditRequestDto.getAccountNumber());
		credit.setCci(creditRequestDto.getCci());
		credit.setAmount(creditRequestDto.getAmount());
		credit.setCreditLimit(creditRequestDto.getCreditLimit());
		Customer customer = new Customer();
		customer.setId(creditRequestDto.getCustomerId());
		credit.setCustomer(customer);
		return credit;
	}
}
