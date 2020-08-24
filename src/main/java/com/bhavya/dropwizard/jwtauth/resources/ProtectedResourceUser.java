package com.bhavya.dropwizard.jwtauth.resources;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.bhavya.dropwizard.jwtauth.resources.dto.ProtectedResourceResponse;
import com.bhavya.dropwizard.jwtauth.auth.jwt.User;
import com.bhavya.dropwizard.jwtauth.auth.jwt.UserRoles;

import io.dropwizard.auth.Auth;

@Path("/protected")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProtectedResourceUser {

	@GET
	@Path("user")
	@RolesAllowed({ UserRoles.USER, UserRoles.ADMIN})
	public ProtectedResourceResponse getAuthProcessingTime(@Auth User user) {

			return new ProtectedResourceResponse(user.getRoles(), user.getName());

	}
}
