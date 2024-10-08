package com.ing.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class EmployeeRequest {

	private String username;
	private String password;
	private String name;
	private String surname;
	private String email;
	private String phone;
	@Schema(description = "Used to make the user active/passive", defaultValue = "true")
	private boolean enabled;

}
