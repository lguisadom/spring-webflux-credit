package com.nttdata.lagm.credit.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AvailableBalanceResponseDto {
	private String accountNumber;
	private String balance;
}
