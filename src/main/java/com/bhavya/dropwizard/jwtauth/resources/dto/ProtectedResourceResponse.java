package com.bhavya.dropwizard.jwtauth.resources.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Reponse returned to user for demo purposes, indicating username and role that was present in JWT.
 *
 */
public class ProtectedResourceResponse {

	public ProtectedResourceResponse(String role, String username) {
		this.role = role;
		this.username = username;
	}

	@JsonProperty("role")
	private String role;

	@JsonProperty("username")
	private String username;
}
