package com.ing.entity;

import java.math.BigDecimal;
import java.util.Currency;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
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
@Table(name = "Accounts")
public class Account {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;


	@Column(length = 34)
	private String iban;

	private BigDecimal balance;

	@Schema(description = "Currency Code", defaultValue = "TRY")
	private Currency currency = Currency.getInstance("TRY");
	
	@OneToOne(cascade = CascadeType.ALL)
	private Customer customer;

}
