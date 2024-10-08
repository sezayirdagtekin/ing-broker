package com.ing.response;

import java.math.BigDecimal;

import com.ing.enums.Side;
import com.ing.enums.Status;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Builder
public class OrderResponse {
	
	private String assetCode;

	@Enumerated(EnumType.STRING)
	private Side orderSide; // BUY, SELL

	private Integer size;

	private BigDecimal price;
	
	@Enumerated(EnumType.STRING)
	private Status status; // PENDING, MATCHED, CANCELED



}
