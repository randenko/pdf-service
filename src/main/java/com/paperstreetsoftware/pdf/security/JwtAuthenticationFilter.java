package com.paperstreetsoftware.pdf.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import com.paperstreetsoftware.pdf.config.props.SecurityProperties;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Optional;

@Component
public class JwtAuthenticationFilter extends GenericFilterBean {

    private final SecurityProperties securityProperties;

    @Autowired
    public JwtAuthenticationFilter(SecurityProperties securityProperties) {
        this.securityProperties = securityProperties;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;

        Optional<String> token = Optional.ofNullable(httpRequest.getHeader(securityProperties.getHeaderName()));
        if (token.isPresent()) {
            SecurityContext securityContext = SecurityContextHolder.getContext();
            securityContext.setAuthentication(new PreAuthenticatedAuthenticationToken(token.get(), null));
        }
        chain.doFilter(request, response);
    }
}
