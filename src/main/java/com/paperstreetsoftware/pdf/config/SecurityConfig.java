package com.paperstreetsoftware.pdf.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.paperstreetsoftware.pdf.security.TokenAuthenticationFilter;
import com.paperstreetsoftware.pdf.security.TokenAuthenticationProvider;

import javax.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
@Order(1)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final TokenAuthenticationFilter tokenAuthenticationFilter;
    private final TokenAuthenticationProvider tokenAuthenticationProvider;

    @Autowired
    public SecurityConfig(TokenAuthenticationFilter tokenAuthenticationFilter, TokenAuthenticationProvider tokenAuthenticationProvider) {
        this.tokenAuthenticationFilter = tokenAuthenticationFilter;
        this.tokenAuthenticationProvider = tokenAuthenticationProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) {
        authenticationManagerBuilder.authenticationProvider(tokenAuthenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.
                antMatcher("/api/**")
                    .csrf()
                        .disable()
                .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                    .addFilterBefore(tokenAuthenticationFilter, BasicAuthenticationFilter.class)
                .authorizeRequests()
                    .anyRequest()
                        .authenticated()
                .and()
                    .exceptionHandling().authenticationEntryPoint((request, response, authException) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED));
    }
}
