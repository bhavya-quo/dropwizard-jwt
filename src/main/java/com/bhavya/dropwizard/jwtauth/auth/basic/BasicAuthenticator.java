package com.bhavya.dropwizard.jwtauth.auth.basic;

import java.util.Optional;

import com.bhavya.dropwizard.jwtauth.auth.Secrets;
import com.bhavya.dropwizard.jwtauth.resources.LoginResource;

import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.PrincipalImpl;
import io.dropwizard.auth.basic.BasicCredentials;

/**
 * Validates credentials for basic auth on login in {@link LoginResource}.
 *
 */
public class BasicAuthenticator implements Authenticator<BasicCredentials, PrincipalImpl> {

	@Override
	public Optional<PrincipalImpl> authenticate(BasicCredentials credentials) {
		if (isValidCredentials(credentials)) {
			return Optional.of(new PrincipalImpl(credentials.getUsername()));
		}
		return Optional.empty();
	}

	// This code should ideally validate username and password from database instead of constants
	private boolean isValidCredentials(BasicCredentials credentials) {
		return Secrets.LOGIN_USERNAME.equals(credentials.getUsername()) && (Secrets.LOGIN_PASSWORD.equals(credentials.getPassword()));
	}
}
