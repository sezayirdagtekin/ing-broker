package com.ing.util;

import java.util.Random;

import org.iban4j.CountryCode;
import org.iban4j.Iban;

public class IbanUtilty {
	
	
private  IbanUtilty() {}

static final 	Random random = new Random();

public static String generateIBAN() {
		
	
	String bankCode = String.format("%05d", random.nextInt(100000));

	String branchCode = String.format("%05d", random.nextInt(100000));

	String accountNumber = String.format("%016d", random.nextLong() & Long.MAX_VALUE).substring(0, 15);

	return  new Iban.Builder()
                .countryCode(CountryCode.TR)
                .bankCode(bankCode)
                .branchCode(branchCode)
                .accountNumber(accountNumber)
                .nationalCheckDigit("90")
                .build().toString();
	}

}
