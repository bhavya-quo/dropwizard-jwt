package com.bhavya.dropwizard.jwtauth.auth;


import com.bhavya.dropwizard.jwtauth.auth.jwt.JwtAuthoriser;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import org.jose4j.jwt.consumer.JwtContext;
import org.jose4j.keys.HmacKey;
import com.github.toastshaman.dropwizard.auth.jwt.JwtAuthFilter;
import com.bhavya.dropwizard.jwtauth.auth.basic.BasicAuthenticator;
import com.bhavya.dropwizard.jwtauth.auth.jwt.User;
import com.bhavya.dropwizard.jwtauth.auth.jwt.JwtAuthenticator;

import io.dropwizard.auth.AuthFilter;
import io.dropwizard.auth.PrincipalImpl;
import io.dropwizard.auth.basic.BasicCredentialAuthFilter;


public class AuthFilterUtils {

  /*
   * This method builds a auth filter for given Principal for Basic Authentication(User name and password based)
   */
  public BasicCredentialAuthFilter<PrincipalImpl> buildBasicAuthFilter() {
    return new BasicCredentialAuthFilter.Builder<PrincipalImpl>().setAuthenticator(new BasicAuthenticator()).setPrefix("Basic")
                                                                 .buildAuthFilter();
  }

  /*
   * This method builds an auth filter for given
   */
  public AuthFilter<JwtContext, User> buildJwtAuthFilter() {
    // These requirements would be tightened up for production use
    final JwtConsumer consumer = new JwtConsumerBuilder().setAllowedClockSkewInSeconds(300).setRequireSubject()
                                                         .setVerificationKey(new HmacKey(Secrets.JWT_SECRET_KEY)).build();

    return new JwtAuthFilter.Builder<User>().setJwtConsumer(consumer).setRealm("realm").setPrefix("Bearer")
                                            .setAuthenticator(new JwtAuthenticator()).setAuthorizer(new JwtAuthoriser()).buildAuthFilter();
  }
}
