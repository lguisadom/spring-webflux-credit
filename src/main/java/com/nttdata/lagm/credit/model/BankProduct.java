package com.nttdata.lagm.credit.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import lombok.Data;

@Data
public abstract class BankProduct {
	@Id
	@JsonSerialize(using = ToStringSerializer.class)
	private ObjectId id;
	private String accountNumber;
	private String cci;
	private String customerId;
	private String amount;
	private Boolean status = true;
}