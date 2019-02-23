package com.guillem.gvilachess.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

@Component
public class TokenHandler {

    final long EXPIRATIONTIME = 120 * 60 * 1000;        // 15 minutes
    final String SECRET = "ThisIsASecret";            // private key, better read it from an external file

    final public String TOKEN_PREFIX = "Bearer";            // the prefix of the token in the http header
    final public String HEADER_STRING = "Authorization";    // the http header containing the prexif + the token

    @Autowired
    private UserDetailsService userDetailsService;

    public String build(String username) {

        Date now = new Date();

        String JWT = Jwts.builder()
                .setId(UUID.randomUUID().toString())
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATIONTIME))
                //.compressWith(CompressionCodecs.DEFLATE) // uncomment to enable token compression
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();

        return JWT;

    }

    public String parse(String token) {

        String username = Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                .getBody()
                .getSubject();

        return userDetailsService.loadUserByUsername(username).getUsername();

    }

}
