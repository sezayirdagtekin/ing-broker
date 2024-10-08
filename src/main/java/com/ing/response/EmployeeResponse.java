package com.ing.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class EmployeeResponse {

	private String username;
	private String name;
	private String surname;
	private String email;
	private String phone;
	private boolean enabled;

}
