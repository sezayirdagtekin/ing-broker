package com.ing.request;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ing.enums.Side;
import com.ing.enums.Status;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderRequest {

	@ManyToOne
	private String customerUsername;

	private String assetCode;

	@Enumerated(EnumType.STRING)
	private Side orderSide; // BUY, SELL

	private Integer size;

	private BigDecimal price;

	@Enumerated(EnumType.STRING)
	private Status status; // PENDING, MATCHED, CANCELED
	
}
