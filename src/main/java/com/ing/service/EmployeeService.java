package com.ing.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ing.entity.Authorization;
import com.ing.entity.Employee;
import com.ing.entity.User;
import com.ing.exception.EmployeeNotFoundException;
import com.ing.repository.AuthorizationRepository;
import com.ing.repository.EmployeeRepository;
import com.ing.request.EmployeeRequest;

import jakarta.transaction.Transactional;

@Service
public class EmployeeService {

	static Logger log= LoggerFactory.getLogger(EmployeeService.class);
	
	private final EmployeeRepository employeeRepository;
	private final PasswordEncoder passwordEncoder;
	private final  AuthorizationRepository authorizationRepository;


	public EmployeeService(EmployeeRepository employeeRepository, PasswordEncoder passwordEncoder,AuthorizationRepository authorizationRepository) {

		this.employeeRepository = employeeRepository;
		this.passwordEncoder = passwordEncoder;
		this.authorizationRepository = authorizationRepository;
	}

	public Employee findByUserUsername(String username) throws EmployeeNotFoundException {

		return employeeRepository.findByUserUsername(username)
				.orElseThrow(() -> new EmployeeNotFoundException("Employee not found with user name:" + username));
	}
	
	@Transactional
	public Employee create(EmployeeRequest request)  {

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
	
		Employee employee = new Employee();
		employee.setUser(user);
		
		Employee savedEmployee=	employeeRepository.saveAndFlush(employee);
      
		
		authorization.setUser(savedEmployee.getUser());
		authorizationRepository.save(authorization);
		
		log.info("Employee {}  {} create  with role {}",savedEmployee.getUser().getName(),savedEmployee.getUser().getSurname() ,user.getAuthorizations());
	
		return  savedEmployee;
		
		
	}

}
