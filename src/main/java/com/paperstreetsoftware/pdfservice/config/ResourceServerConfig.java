package com.paperstreetsoftware.pdfservice.config;

import com.paperstreetsoftware.pdfservice.config.props.SecurityProperties;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import java.io.IOException;

import static java.nio.charset.StandardCharsets.UTF_8;

@Configuration
@EnableResourceServer
@EnableConfigurationProperties(SecurityProperties.class)
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    private static final String ROOT_PATTERN = "/**";
    private static final String ACTUATOR_PATTERN = "/actuator/**";

    private final SecurityProperties securityProperties;

    @Autowired
    public ResourceServerConfig(SecurityProperties securityProperties) {
        this.securityProperties = securityProperties;
    }

    @Override
    public void configure(final ResourceServerSecurityConfigurer resources) {
        resources.tokenStore(tokenStore());
    }

    @Override
    public void configure(final HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers(ACTUATOR_PATTERN)
                    .permitAll()
                .antMatchers(ROOT_PATTERN)
                    .authenticated();
    }

    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(tokenEnhancer());
    }

    @Bean
    public JwtAccessTokenConverter tokenEnhancer() {
        try {
            JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
            converter.setVerifierKey(IOUtils.toString(securityProperties.getPublicKey().getInputStream(), UTF_8));
            return converter;
        } catch (IOException e) {
            throw new RuntimeException("An error occurred while reading the public key.", e);
        }
    }
}
