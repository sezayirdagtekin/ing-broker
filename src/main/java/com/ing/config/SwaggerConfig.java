package com.ing.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class SwaggerConfig {

	private static final String JAVA_IN_USE_SECURITY_SCHEME = "JavaInUseSecurityScheme";

	@Bean
	OpenAPI customOpenAPI() {

		return new OpenAPI().info(new Info().title(" ING Broker Application"))
				.addSecurityItem(new SecurityRequirement().addList(JAVA_IN_USE_SECURITY_SCHEME))
				.components(new Components().addSecuritySchemes(JAVA_IN_USE_SECURITY_SCHEME,
						new SecurityScheme().name(JAVA_IN_USE_SECURITY_SCHEME).type(SecurityScheme.Type.HTTP)
								.scheme("bearer").bearerFormat("JWT")));

	}
}