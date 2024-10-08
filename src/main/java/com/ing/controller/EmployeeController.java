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

import com.ing.entity.Employee;
import com.ing.exception.EmployeeNotFoundException;
import com.ing.request.EmployeeRequest;
import com.ing.response.EmployeeResponse;
import com.ing.service.EmployeeService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/employee")
@Tag(name = "Employee Controller", description = "Controller for investment account operations")
public class EmployeeController {

	private EmployeeService service;

	public EmployeeController(EmployeeService service) {
		this.service = service;
	}

	@PostMapping("/create")
	@PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
	@Operation(summary = "Create a new employee", description = "Creates a new employee for the application")
	public ResponseEntity<EmployeeResponse> createEmployee(@RequestBody EmployeeRequest request) {

		Employee body = service.create(request);
		EmployeeResponse response= EmployeeResponse.builder()
						.name(body.getUser().getName())
						.surname(body.getUser().getSurname())
						.username(body.getUser().getUsername())
						.email(body.getUser().getEmail())
						.phone(body.getUser().getPhone())
						.enabled(body.getUser().isEnabled())
						.build();
						
		return new ResponseEntity<>(response, HttpStatus.CREATED);

	}

	@GetMapping("/{username}")
	@PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
	@Operation(summary = "Get employee by username", description = "Get employee details by username")
	public ResponseEntity<EmployeeResponse> getEmployeeByUsername(@PathVariable String username)
			throws EmployeeNotFoundException {
		
		
		Employee body = service.findByUserUsername(username);
		EmployeeResponse response= EmployeeResponse.builder()
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
