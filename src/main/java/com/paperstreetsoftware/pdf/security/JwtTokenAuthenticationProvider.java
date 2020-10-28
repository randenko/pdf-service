package com.paperstreetsoftware.pdf.security;

import com.paperstreetsoftware.pdf.config.props.SecurityProperties;
import com.paperstreetsoftware.pdf.security.exception.JwtAuthenticationException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;

@Component
public class JwtTokenAuthenticationProvider implements AuthenticationProvider {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtTokenAuthenticationProvider.class);


    private final SecurityProperties securityProperties;
    private PublicKey publicKey;

    @Autowired
    public JwtTokenAuthenticationProvider(SecurityProperties securityProperties) {
        this.securityProperties = securityProperties;
    }

    @PostConstruct
    public void init() throws NoSuchAlgorithmException, IOException, InvalidKeySpecException {
        try (InputStream publicKeyStream = securityProperties.getPublicKey()) {
            KeyFactory kf = KeyFactory.getInstance("RSA");
            publicKey = kf.generatePublic(new X509EncodedKeySpec(IOUtils.toByteArray(publicKeyStream)));
        }
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
            throw new JwtAuthenticationException("An error occurred while validating the jtw token.", t);
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(PreAuthenticatedAuthenticationToken.class);
    }
}
