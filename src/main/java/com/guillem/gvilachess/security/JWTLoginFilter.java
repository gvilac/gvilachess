package com.guillem.gvilachess.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.guillem.gvilachess.model.domain.user.Account;
import com.guillem.gvilachess.security.service.TokenAuthenticationService;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

public class JWTLoginFilter extends AbstractAuthenticationProcessingFilter {
	
	private TokenAuthenticationService tokenAuthenticationService;

	public JWTLoginFilter(String url, AuthenticationManager authManager, ApplicationContext ctx) {
		super(new AntPathRequestMatcher(url));
		this.tokenAuthenticationService = ctx.getBean(TokenAuthenticationService.class);
		setAuthenticationManager(authManager);
	}
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) throws AuthenticationException, IOException, ServletException {
		// Retrieve username and password from the http request and save them in an Account object.
		Account account = new ObjectMapper().readValue(req.getInputStream(), Account.class);
		
		// Verify if the correctness of login details.
		// If correct, the successfulAuthentication() method is executed.
		return getAuthenticationManager().authenticate(
				new UsernamePasswordAuthenticationToken(
						account.getUsername(),
						account.getPassword(),
						Collections.emptyList()
						)
				);
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain, Authentication auth) throws IOException, ServletException {
		// Pass authenticated user data to the tokenAuthenticationService in order to add a JWT to the http response.
		tokenAuthenticationService.addAuthentication(res, auth);
	}

}
