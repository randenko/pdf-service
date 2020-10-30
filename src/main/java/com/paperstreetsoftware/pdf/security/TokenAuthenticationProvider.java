package com.paperstreetsoftware.pdf.security;

import static java.util.Optional.ofNullable;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;

import com.paperstreetsoftware.pdf.security.exception.TokenAuthenticationException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;

@Component
public class TokenAuthenticationProvider implements AuthenticationProvider {

    private static final Logger LOGGER = LoggerFactory.getLogger(TokenAuthenticationProvider.class);

    private final PublicKey publicKey;

    @Autowired
    public TokenAuthenticationProvider(final PublicKey publicKey) {
        this.publicKey = publicKey;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String token = String.valueOf(authentication.getPrincipal());
        try {
            Jws<Claims> jws = Jwts.parser()
                    .setSigningKey(publicKey)
                    .requireIssuer("com.paperstreetsoftware.auth")
                    .parseClaimsJws(token);
            LOGGER.info(String.format("Jwt token valid. Header=%s, Claims=%s", jws.getHeader(), jws.getBody()));
            UserDetails userDetails = buildPrincipal(jws);
            return new PreAuthenticatedAuthenticationToken(userDetails, "{redacted}", userDetails.getAuthorities());
        } catch (Throwable t) {
            throw new TokenAuthenticationException("An error occurred while validating the jtw token.", t);
        }
    }

    private UserDetails buildPrincipal(Jws<Claims> jws) {
        return User.builder().username(getClaim(jws, "username"))
                .password("{redacted}")
                .accountExpired(!Boolean.valueOf(getClaim(jws, "isAccountNonExpired")))
                .accountLocked(!Boolean.valueOf(getClaim(jws, "isAccountNonLocked")))
                .credentialsExpired(!Boolean.valueOf(getClaim(jws, "isCredentialsNonExpired")))
                .disabled(!Boolean.valueOf(getClaim(jws, "isEnabled")))
                .authorities(getAuthorityClaims(jws))
                .build();
    }

    private String getClaim(Jws<Claims> jws, String name) {
        return ofNullable(jws.getBody().get(name))
                .map(String::valueOf)
                .map(String::trim)
                .orElseThrow(() -> new BadCredentialsException(String.format("'%s' must be in claim.", name)));
    }

    @SuppressWarnings("unchecked")
    private List<GrantedAuthority> getAuthorityClaims(Jws<Claims> jws) {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        ofNullable(jws.getBody().get("authorities")).map(object -> (ArrayList<String>) object)
                .ifPresent(authorities -> authorities.stream().map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toCollection(() -> grantedAuthorities)));
        return grantedAuthorities;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(PreAuthenticatedAuthenticationToken.class);
    }
}
