package com.ing.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ing.entity.Authorization;
import com.ing.entity.Customer;
import com.ing.entity.User;
import com.ing.exception.CustomerNotFoundException;
import com.ing.repository.AuthorizationRepository;
import com.ing.repository.CustomerRepository;
import com.ing.request.CustomerRequest;

import jakarta.transaction.Transactional;

@Service
public class CustomerService {

	static final Logger log= LoggerFactory.getLogger(CustomerService.class);
	private final  CustomerRepository customerRepository;
	private final PasswordEncoder passwordEncoder;
	private final  AuthorizationRepository authorizationRepository;



	public CustomerService(CustomerRepository customerRepository, PasswordEncoder passwordEncoder,
			AuthorizationRepository authorizationRepository) {
		this.customerRepository = customerRepository;
		this.passwordEncoder = passwordEncoder;
		this.authorizationRepository = authorizationRepository;
	}


	@Transactional
	public Customer register(CustomerRequest request)  {

		 List<Authorization> roles= new ArrayList<>();
		 Authorization authorization= new Authorization();
		 authorization.setAuth("ROLE_USER");
		 roles.add(authorization);
		 
		User user= User.builder()
				.username(request.getUsername())
				.password(passwordEncoder.encode(request.getPassword()))
				.name(request.getName())
				.surname(request.getSurname())
				.email(request.getEmail())
				.phone(request.getPhone())
				.enabled(true)
				.authorizations(roles)
				.build();
	
		Customer customer = new Customer();
		customer.setUser(user);

		Customer savedCustomer = customerRepository.saveAndFlush(customer);
      
		
		authorization.setUser(savedCustomer.getUser());
		authorizationRepository.save(authorization);
		
		log.info("Customer {}  {} create  with role {}",savedCustomer.getUser().getName(),savedCustomer.getUser().getSurname() ,user.getAuthorizations());
	
		return  savedCustomer;
		
		
	}


	public Customer findByUsername(String username) throws CustomerNotFoundException {

		return customerRepository.findByUserUsername(username)
				.orElseThrow(() -> new CustomerNotFoundException("Customer not found with user name:" + username));
	}

}