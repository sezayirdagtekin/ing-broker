package com.ing.controller;

import java.util.ArrayList;
import java.util.List;

import javax.security.auth.login.AccountNotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ing.entity.Account;
import com.ing.exception.CustomerNotFoundException;
import com.ing.exception.InsufficientBalanceException;
import com.ing.request.AccountRequest;
import com.ing.request.MoneyTransferRequest;
import com.ing.response.AccountResponse;
import com.ing.service.AccountService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/account")
@Tag(name = "Investment Bank Account Controller", description = "Controller for investment account operations.Used for customer money transfers")
public class AccountController {
	
	private AccountService service;
	
	public AccountController(AccountService service) {
		this.service = service;
	}



	@PostMapping("/create")
	@PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
	@Operation(summary = "Create new investment account(IBAN)", description = "Create new  investment bank account. You can open an account in different currencies like TRY,USD..")
	public ResponseEntity<AccountResponse> createAccount(@RequestBody AccountRequest request) throws CustomerNotFoundException {
		
		Account body=service.create(request);
		 AccountResponse response= AccountResponse.builder()
					.name(body.getCustomer().getUser().getName())
					.surname(body.getCustomer().getUser().getSurname())
		            .iban(body.getIban())
		            .currency(body.getCurrency())
		            .balance(body.getBalance())
					.build();

		return new ResponseEntity<>(response, HttpStatus.CREATED);
		
	}

	@PutMapping("/deposit")
	@PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
	@Operation(summary = "Transfer money", description = "Transfers money to the given IBAN number")
	public ResponseEntity<AccountResponse> depositMoney(@RequestBody MoneyTransferRequest request) throws AccountNotFoundException {
		
		Account body =	service.deposit(request.getIban(), request.getAmount());
		AccountResponse response= AccountResponse.builder()
					.name(body.getCustomer().getUser().getName())
					.surname(body.getCustomer().getUser().getSurname())
		            .iban(body.getIban())
		            .currency(body.getCurrency())
		            .balance(body.getBalance())
					.build();		
		 return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PutMapping("/withdraw")
	@PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
	@Operation(summary = "Withdraw money", description = "withdraws money from the given IBAN number")
	public ResponseEntity<AccountResponse> withdrawMoney(@RequestBody MoneyTransferRequest request)
			throws AccountNotFoundException, InsufficientBalanceException {
		 Account body =	service.withdraw(request.getIban(), request.getAmount());
		 AccountResponse response= AccountResponse.builder()
					.name(body.getCustomer().getUser().getName())
					.surname(body.getCustomer().getUser().getSurname())
		            .iban(body.getIban())
		            .currency(body.getCurrency())
		            .balance(body.getBalance())
					.build();
		
		 return new ResponseEntity<>(response, HttpStatus.OK);

	}

	@GetMapping("/iban")
	@PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
	@Operation(summary = "Get account by IBAN number", description = "Get account detail by IBAN number")
	public ResponseEntity<AccountResponse> getAccountByIban(@RequestParam String iban) throws AccountNotFoundException {
		Account body = service.getAccountByIban(iban)
				.orElseThrow(() -> new AccountNotFoundException("Account not found"));
		 AccountResponse response= AccountResponse.builder()
				.name(body.getCustomer().getUser().getName())
				.surname(body.getCustomer().getUser().getSurname())
	            .iban(body.getIban())
	            .currency(body.getCurrency())
	            .balance(body.getBalance())
				.build();

		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping("/all")
	@PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
	@Operation(summary = "All Ibans", description = "Get all ibans")
	public ResponseEntity<List<AccountResponse>> findAllAccounts() {

		List<AccountResponse> responseList = new ArrayList<>();
		List<Account> accounts = service.findAll();
		accounts.forEach(body -> {
			AccountResponse response = AccountResponse.builder()
					.name(body.getCustomer().getUser().getName())
					.surname(body.getCustomer().getUser().getSurname())
					.iban(body.getIban())
					.currency(body.getCurrency())
					.balance(body.getBalance())
					.build();
			responseList.add(response);
		});

		return new ResponseEntity<>(responseList, HttpStatus.OK);
	}

}
