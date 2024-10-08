package com.ing.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.ing.enums.Side;
import com.ing.enums.Status;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "Orders")
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.EAGER)
	private Customer customer;

	private String assetCode;

	@Enumerated(EnumType.STRING)
	private Side orderSide; // BUY, SELL

	private Integer size;

	private BigDecimal price;
	@Enumerated(EnumType.STRING)
	private Status status; // PENDING, MATCHED, CANCELED

	private LocalDateTime createDate;

}
