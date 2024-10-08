package com.ing.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CustomerResponse {

	private String username;
	private String name;
	private String surname;
	private String email;
	private String phone;
	private boolean enabled;

}
