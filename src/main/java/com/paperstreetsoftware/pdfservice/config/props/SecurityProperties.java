package com.paperstreetsoftware.pdfservice.config.props;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.InputStream;

@ConfigurationProperties("security.jwt")
public class SecurityProperties {

    private String headerName;
    private InputStream publicKey;

    public String getHeaderName() {
        return headerName;
    }

    public void setHeaderName(String headerName) {
        this.headerName = headerName;
    }

    public InputStream getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(InputStream publicKey) {
        this.publicKey = publicKey;
    }
}
