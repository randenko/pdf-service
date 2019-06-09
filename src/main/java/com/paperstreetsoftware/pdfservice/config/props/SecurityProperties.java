package com.paperstreetsoftware.pdfservice.config.props;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.io.Resource;

@ConfigurationProperties("security.jwt")
public class SecurityProperties {

    private Resource publicKey;

    public Resource getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(Resource publicKey) {
        this.publicKey = publicKey;
    }
}
