package com.bhavya.dropwizard.jwtauth.resources;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static org.jose4j.jws.AlgorithmIdentifiers.HMAC_SHA256;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import com.bhavya.dropwizard.jwtauth.resources.dto.LoginResponse;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.keys.HmacKey;
import org.jose4j.lang.JoseException;
import com.bhavya.dropwizard.jwtauth.auth.Secrets;
import com.bhavya.dropwizard.jwtauth.auth.jwt.UserRoles;

import io.dropwizard.auth.Auth;
import io.dropwizard.auth.PrincipalImpl;
import io.dropwizard.jersey.caching.CacheControl;

@Path("auth")
@Produces(APPLICATION_JSON)
public class LoginResource {

	@GET
	@Path("/login")
	@CacheControl(noCache = true, noStore = true, mustRevalidate = true, maxAge = 10)
	public final LoginResponse doLogin(@Auth PrincipalImpl user) throws JoseException {
		/*
		@Auth annotation is used by DynamicFeature to identify where authentication filters need to be applied
		 */
		return new LoginResponse(buildToken(user).getCompactSerialization());
	}

	/**
	 * Example token builder. This would be handled by some role mapping system in production.
	 *
	 * @return Example token with the {@link UserRoles#USER} role.
	 */
	private JsonWebSignature buildToken(PrincipalImpl user) {
		// These claims would be tightened up for production
		final JwtClaims claims = new JwtClaims();
		// value of id
		claims.setSubject("1");
		// value of user role to be authorized later - modify this to admin if needed
		// ideally this should be queried from database table and then applied here
		claims.setStringClaim("roles", UserRoles.USER);
		// value of user name
		claims.setStringClaim("user", user.getName());
		claims.setIssuedAtToNow();
		claims.setGeneratedJwtId();

		final JsonWebSignature jws = new JsonWebSignature();
		jws.setPayload(claims.toJson());
		jws.setAlgorithmHeaderValue(HMAC_SHA256);
		jws.setKey(new HmacKey(Secrets.JWT_SECRET_KEY));
		return jws;
	}
}
