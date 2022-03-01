package com.nttdata.lagm.model;


import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "credit")

public class credit {
	@Id
	private String accountNumber;
	private String cci;
	private String amount;
	private List<BankingMovement> listBankingMovements;
	private Long customerId; // beneficiary // Long
	private Integer typeId;
	private String name;
	private String maintenanceFee;
	private Integer maxLimitMonthlyMovements;
	private Integer dayAllowed;



	private Double creditLimit;
}

