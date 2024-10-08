package com.ing.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ing.entity.Customer;
import com.ing.exception.CustomerNotFoundException;
import com.ing.request.CustomerRequest;
import com.ing.response.CustomerResponse;
import com.ing.service.CustomerService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@RequestMapping("/customer")
@Tag(name = "Customer Controller", description = "Controller for customer operations")
public class CustomerController {

	private CustomerService service;
 
	public CustomerController(CustomerService service) {
		this.service = service;
	}

	
	@PostMapping("/register")
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
	@Operation(summary ="Create a new customer" ,description = "Creates a new customer for the application")
	public ResponseEntity<CustomerResponse> register(@RequestBody CustomerRequest request) 
	{
		
		Customer body=service.register(request);
		CustomerResponse response= CustomerResponse.builder()
				.username(body.getUser().getUsername())
				.name(body.getUser().getName())
				.surname(body.getUser().getSurname())
				.email(body.getUser().getEmail())
				.phone(body.getUser().getPhone())
				.enabled(body.getUser().isEnabled())
				.build();

		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	@GetMapping("/{username}")
	@PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
	@Operation(summary ="Get customer by username" ,description = "Get customer details by username")
	public ResponseEntity<CustomerResponse> findByUsername(@PathVariable String username) throws CustomerNotFoundException {
		
		 Customer body = service.findByUsername(username);
		 CustomerResponse response= CustomerResponse.builder()
				.username(body.getUser().getUsername())
				.name(body.getUser().getName())
				.surname(body.getUser().getSurname())
				.email(body.getUser().getEmail())
				.phone(body.getUser().getPhone())
				.enabled(body.getUser().isEnabled())
				.build();

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
