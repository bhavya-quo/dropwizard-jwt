package com.bhavya.dropwizard.jwtauth.health;

import com.codahale.metrics.health.HealthCheck;

public class JwtDemoHealthCheck extends HealthCheck {


	@Override
	protected Result check() {
			return Result.healthy();
	}
}
