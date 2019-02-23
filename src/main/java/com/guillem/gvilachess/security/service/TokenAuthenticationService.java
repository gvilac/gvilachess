package com.guillem.gvilachess.security.service;

import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface TokenAuthenticationService {

	void addAuthentication(HttpServletResponse res, Authentication authentication);

	Authentication getAuthentication(HttpServletRequest request);

}