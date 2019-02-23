package com.guillem.gvilachess.security.service;

import com.guillem.gvilachess.security.TokenHandler;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static java.util.Collections.emptyList;

@Service
public class TokenAuthenticationServiceImpl implements TokenAuthenticationService {

	@Autowired
	private TokenHandler tokenHandler;

	@Override
	public void addAuthentication(HttpServletResponse res, Authentication authentication) {
		String user = authentication.getName();
		String JWT = tokenHandler.build(user);
		res.addHeader(tokenHandler.HEADER_STRING, tokenHandler.TOKEN_PREFIX + " " + JWT);
	}

	@Override
	public Authentication getAuthentication(HttpServletRequest request) {
		
		String token = request.getHeader(tokenHandler.HEADER_STRING);
		
		if (token != null && token.startsWith(tokenHandler.TOKEN_PREFIX)) {
			// Parse the token.
			String user = null;
			
			try {
				user = tokenHandler.parse(token);
			} catch (ExpiredJwtException e) {
				e.printStackTrace();
			} catch (UnsupportedJwtException e) {
				e.printStackTrace();
			} catch (MalformedJwtException e) {
				e.printStackTrace();
			} catch (SignatureException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			}
			
			if (user != null) {
				return new UsernamePasswordAuthenticationToken(user, null, emptyList());
			} else {
				return null;
			}
		}
		return null;
	}
}
