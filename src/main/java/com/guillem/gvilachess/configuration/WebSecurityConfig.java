package com.guillem.gvilachess.configuration;

import com.guillem.gvilachess.repository.AccountRepository;
import com.guillem.gvilachess.security.JWTAuthenticationEntryPoint;
import com.guillem.gvilachess.security.JWTAuthenticationFilter;
import com.guillem.gvilachess.security.JWTLoginFilter;
import com.guillem.gvilachess.security.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Created by guillem on 16/02/2019.
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Configuration
@EnableWebSecurity(debug = true)
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private JWTAuthenticationEntryPoint unauthorizedHandler;
    private AccountRepository accountRepository;

    @Autowired
    public WebSecurityConfig(JWTAuthenticationEntryPoint unauthorizedHandler, AccountRepository accountRepository) {
        this.unauthorizedHandler = unauthorizedHandler;
        this.accountRepository = accountRepository;
    }

    public WebSecurityConfig() {
    }

    public WebSecurityConfig(boolean disableDefaults) {
        super(disableDefaults);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // Disable CSRF protection since tokens are immune to it
                .csrf().disable()
                // If the user is not authenticated, returns 401
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                // This is a stateless application, disable sessions
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                // Security policy
                .authorizeRequests()
                // Allow access to swagger
                .antMatchers(
                        HttpMethod.GET,
                        "/",
                        "/v2/api-docs",           // swagger
                        "/webjars/**",            // swagger-ui webjars
                        "/swagger-resources/**",  // swagger-ui resources
                        "/configuration/**",      // swagger configuration
                        "/*.html",
                        "/favicon.ico",
                        "/**/*.html",
                        "/**/*.css",
                        "/**/*.js"
                ).permitAll()
                // Allow anonymous access to "/login" (only POST requests)
                .antMatchers(HttpMethod.POST,
                        "/login",
                        "/api/account/create").permitAll()
                // Any other request must be authenticated
                .anyRequest().authenticated().and()
                // Custom filter for logging in users at "/login"
                .addFilterBefore(new JWTLoginFilter("/login", authenticationManager(), getApplicationContext()), UsernamePasswordAuthenticationFilter.class)
                // Custom filter for authenticating users using tokens
                .addFilterBefore(new JWTAuthenticationFilter(getApplicationContext()), UsernamePasswordAuthenticationFilter.class)
                // Disable resource caching
                .headers().cacheControl();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(userDetailsServiceBean());

        //auth.userDetailsService(userDetailsService()).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    public UserDetailsService userDetailsServiceBean() throws Exception {
        return new AccountService(accountRepository);
    }

}