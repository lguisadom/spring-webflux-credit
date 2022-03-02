package com.nttdata.lagm.credit.model;


import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Document(collection = "credit")
public class Credit {
	private Long id;
	private String accountNumber;
	private String cci;
	private Long customerId;
	private Double creditLimit;
}

