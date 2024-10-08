package com.ing.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class AccountRequest {

	@NotNull
	private String username;

	@NotNull
	@Schema(description = "Default curry code is TRY.  Accounts can be opened in different currencies such as USD ", defaultValue = "TRY")
	private String currencyCode="TRY";

}
