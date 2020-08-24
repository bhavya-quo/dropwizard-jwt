package com.bhavya.dropwizard.jwtauth.core;

import java.io.UnsupportedEncodingException;

import com.bhavya.dropwizard.jwtauth.health.JwtDemoHealthCheck;
import com.bhavya.dropwizard.jwtauth.resources.ProtectedResourceAdmin;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;
import org.jose4j.jwt.consumer.JwtContext;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.bhavya.dropwizard.jwtauth.auth.jwt.User;
import com.bhavya.dropwizard.jwtauth.auth.AuthFilterUtils;
import com.bhavya.dropwizard.jwtauth.resources.LoginResource;
import com.bhavya.dropwizard.jwtauth.resources.ProtectedResourceUser;

import io.dropwizard.Application;
import io.dropwizard.auth.AuthFilter;
import io.dropwizard.auth.PolymorphicAuthDynamicFeature;
import io.dropwizard.auth.PolymorphicAuthValueFactoryProvider;
import io.dropwizard.auth.PrincipalImpl;
import io.dropwizard.auth.basic.BasicCredentials;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class MainApp extends Application<DemoConfiguration> {

	public static void main(final String[] args) throws Exception {
		new MainApp().run(args);
	}

	@Override
	public String getName() {
		return "DropWizard Auth using JWT";
	}

	@Override
	public void initialize(final Bootstrap<DemoConfiguration> bootstrap) {
		// Nothing required for now
	}

	@Override
	public void run(final DemoConfiguration configuration, final Environment environment) {
		registerResources(environment);

		registerAuthFilters(environment);

	}

	/**
	 * Registers the resources that will be exposed to the user.
	 */
	private void registerResources(Environment environment) {
		environment.healthChecks().register("running", new JwtDemoHealthCheck());
		environment.jersey().register(new LoginResource());
		environment.jersey().register(new ProtectedResourceUser());
		environment.jersey().register(new ProtectedResourceAdmin());
	}

	/**
	 * Registers the filters that will handle authentication
	 */
	private void registerAuthFilters(Environment environment) {
		AuthFilterUtils authFilterUtils = new AuthFilterUtils();
		// Register a basic auth filter for Username and password based authentication
		final AuthFilter<BasicCredentials, PrincipalImpl> basicFilter = authFilterUtils.buildBasicAuthFilter();
		// Register a JWT auth filter for JWT based authentication site
		final AuthFilter<JwtContext, User> jwtFilter = authFilterUtils.buildJwtAuthFilter();

		final PolymorphicAuthDynamicFeature feature = new PolymorphicAuthDynamicFeature<>(
				ImmutableMap.of(PrincipalImpl.class, basicFilter, User.class, jwtFilter));
		final AbstractBinder binder = new PolymorphicAuthValueFactoryProvider.Binder<>(
				ImmutableSet.of(PrincipalImpl.class, User.class));

		environment.jersey().register(feature);
		environment.jersey().register(binder);
		environment.jersey().register(RolesAllowedDynamicFeature.class);
	}
}
