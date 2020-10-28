package com.paperstreetsoftware.pdf.security;

import com.paperstreetsoftware.pdf.security.exception.TokenAuthenticationException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;

import java.security.PublicKey;
import java.util.ArrayList;

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
            String username = String.valueOf(jws.getBody().get("sub"));
            return new PreAuthenticatedAuthenticationToken(username, "{redacted}", new ArrayList<>());
        } catch (Throwable t) {
            throw new TokenAuthenticationException("An error occurred while validating the jtw token.", t);
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(PreAuthenticatedAuthenticationToken.class);
    }
}
