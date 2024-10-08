package com.ing.advice;

import javax.security.auth.login.AccountNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.ing.exception.CustomerNotFoundException;
import com.ing.exception.EmployeeNotFoundException;
import com.ing.exception.InsufficientBalanceException;
import com.ing.exception.InvalidOrderOperationException;
import com.ing.exception.OrderNotFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

	Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@ExceptionHandler(AccountNotFoundException.class)
	public ResponseEntity<String> handleAccountNotFoundException(AccountNotFoundException ex) {
		log.info("Call GlobalExceptionHandler: Account Not Found Exception");
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
	}

	@ExceptionHandler(InsufficientBalanceException.class)
	public ResponseEntity<String> handleInsufficientException(InsufficientBalanceException ex) {
		log.info("call GlobalExceptionHandler  Insufficient Balance Exception");
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
	}

	@ExceptionHandler(CustomerNotFoundException.class)
	public ResponseEntity<String> handleCustomerNotFoundException(CustomerNotFoundException ex) {
		log.info("call GlobalExceptionHandler  Customer Not Found Exception");
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
	}
	
	@ExceptionHandler(EmployeeNotFoundException.class)
	public ResponseEntity<String> handleEmployeeNotFoundException(EmployeeNotFoundException ex) {
		log.info("call GlobalExceptionHandler  Employee Not Found Exception");
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
	}


	@ExceptionHandler(UsernameNotFoundException.class)
	public ResponseEntity<String> handleUserNotFoundException(UsernameNotFoundException ex) {
		log.info("call GlobalExceptionHandler  Username Not Found Exception");
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
	}

	@ExceptionHandler(OrderNotFoundException.class)
	public ResponseEntity<String> handleOrderNotFoundException(OrderNotFoundException ex) {
		log.info("Call GlobalExceptionHandler: Order Not Found Exception");
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
	}
	
	@ExceptionHandler(InvalidOrderOperationException.class)
	public ResponseEntity<String> handleInvalidOrderOperationException(InvalidOrderOperationException ex) {
		log.info("Call GlobalExceptionHandler: Invalid Order Operation Exception");
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
	}
	
	
	
}
