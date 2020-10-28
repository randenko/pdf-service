package com.paperstreetsoftware.pdf.config.props;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.InputStream;

@ConfigurationProperties("security.jwt")
public class SecurityProperties {

    private InputStream publicKey;

    public InputStream getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(InputStream publicKey) {
        this.publicKey = publicKey;
    }
}
