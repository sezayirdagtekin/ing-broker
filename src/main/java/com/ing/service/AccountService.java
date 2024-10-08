package com.ing.service;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;
import java.util.Optional;

import javax.security.auth.login.AccountNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ing.entity.Account;
import com.ing.entity.Customer;
import com.ing.exception.CustomerNotFoundException;
import com.ing.exception.InsufficientBalanceException;
import com.ing.repository.AccountRepository;
import com.ing.request.AccountRequest;
import com.ing.util.IbanUtilty;

import jakarta.transaction.Transactional;

@Service
public class AccountService {

	static final  Logger log= LoggerFactory.getLogger(AccountService.class);

	private final AccountRepository accountRepository;
	private final  CustomerService customerService;


	public AccountService(AccountRepository repository, CustomerService customerService) {
		this.accountRepository = repository;
		this.customerService = customerService;
	}

	
	@Transactional
	public Account deposit(String iban, BigDecimal amount) throws AccountNotFoundException {

		Optional<Account> accountOptinal = accountRepository.findByIban(iban);

		Account account = accountOptinal.orElseThrow(() -> new AccountNotFoundException("Invalid IBAN!!"));
		BigDecimal newBalance = account.getBalance().add(amount);
		account.setBalance(newBalance);
		Account updatedAccount=accountRepository.save(account);
		log.info("{} amount was deposited to iban number{} ",amount,iban);// Fix later: only the first 4 and last two digits will be shown	
        return updatedAccount;
	}

	@Transactional
	public Account withdraw(String iban, BigDecimal amount) throws AccountNotFoundException, InsufficientBalanceException {

		Optional<Account> accountOptinal = accountRepository.findByIban(iban);
		Account account = accountOptinal.orElseThrow(() -> new AccountNotFoundException("Invalid IBAN!!"));
		
		if (account.getBalance().subtract(amount).compareTo(BigDecimal.ZERO) < 0) {
			throw new InsufficientBalanceException("Insufficient balance for account:" + account.getId());

		}
		BigDecimal newBalance = account.getBalance().subtract(amount);
		account.setBalance(newBalance);
		Account updatedAccount=accountRepository.save(account);
		log.info("{} amount was withdrawed from iban number {} ",amount,iban);// Fix later: only the first 4 and last two digits will be shown
		return updatedAccount;
	}

	@Transactional
	public Account create(AccountRequest request) throws  CustomerNotFoundException {

		Customer customer=customerService.findByUsername(request.getUsername());
		Account account = new Account();
		account.setBalance(BigDecimal.ZERO);
		account.setIban(IbanUtilty.generateIBAN());
		account.setCustomer(customer);
		account.setCurrency(Currency.getInstance(request.getCurrencyCode()));
		log.info("For user {}  account is created with currency code {} ", request.getUsername(),account.getCurrency().getCurrencyCode());
		return accountRepository.save(account);
	}

	public Optional<Account> getAccountByIban(String iban) {

		return accountRepository.findByIban(iban);
	}

	public Account findByCustomer(Customer customer) throws AccountNotFoundException {
		
		return  accountRepository.findByCustomer(customer)
				.orElseThrow( ()-> new AccountNotFoundException("Account not found for customer!:"+customer.getUser().getUsername()) );
	}

	public List<Account> findAll() {
		return accountRepository.findAll();
	}

}
