package com.bhavya.dropwizard.jwtauth.resources.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Communicates JWT token to be used after login.
 */
public class LoginResponse {

	public LoginResponse(String token) {
		this.token = token;
	}

	@JsonProperty("token")
	private String token;

}
