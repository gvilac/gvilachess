package com.guillem.gvilachess.security;

import com.guillem.gvilachess.security.service.TokenAuthenticationService;
import com.guillem.gvilachess.security.service.TokenAuthenticationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class JWTAuthenticationFilter extends GenericFilterBean {


	TokenAuthenticationService tokenAuthenticationService;

	public JWTAuthenticationFilter(ApplicationContext ctx) {
		this.tokenAuthenticationService = ctx.getBean(TokenAuthenticationService.class);
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
		
		// Delegates authentication to the TokenAuthenticationService
		Authentication authentication = tokenAuthenticationService.getAuthentication((HttpServletRequest)request);
		
		// Apply the authentication to the SecurityContextHolder
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		// Go on processing the request
		filterChain.doFilter(request,response);
		
		// Clears the context from authentication
		SecurityContextHolder.getContext().setAuthentication(null);
		SecurityContextHolder.clearContext();
	}

}
