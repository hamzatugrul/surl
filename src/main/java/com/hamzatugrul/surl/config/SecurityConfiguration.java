package com.hamzatugrul.surl.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author Hamza Tugrul Topcuoglu
 * @date 2/27/2021
 */

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    // temporary solutions for this assignment. Username and password could be removed
    // and can be retrieved from database if you want to handle security layer in deep.
    @Value("${USER_NAME:admin}")
    private String userName;
    @Value("${PASSWORD:admin}")
    private String password;

    // This could be configurable as well
    //TODO: whitelist need to modified
    private static final String[] WHITELIST = {
            // Exposed APIs
            "/{surl}",
            "/api/v1/shortener",
            "/api/v1/status/{surl}",
            // Swagger Endpoints
            "/v2/api-docs",
            "/swagger-resources",
            "/swagger-resources/**",
            "/swagger-ui/**"
    };

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        // This could be configurable list from database if you want to handle security layer in deep.

        auth.inMemoryAuthentication()
                .withUser(userName)
                .password(passwordEncoder().encode(password))
                .roles("USER");
    }

    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers(WHITELIST).permitAll()
                .anyRequest().authenticated()
                .and()
                .httpBasic();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}