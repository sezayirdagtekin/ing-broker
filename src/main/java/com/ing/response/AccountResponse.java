package com.ing.response;

import java.math.BigDecimal;
import java.util.Currency;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AccountResponse {

	private String name;
	private String surname;
	private String iban;
	private BigDecimal balance;
	private Currency currency;

}
