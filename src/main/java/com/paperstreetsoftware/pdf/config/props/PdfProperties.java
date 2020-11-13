package com.paperstreetsoftware.pdf.config.props;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.io.Resource;

@ConfigurationProperties("pdf")
public class PdfProperties {

    private Resource resourcePath;

    public Resource getResourcePath() {
        return resourcePath;
    }

    public void setResourcePath(Resource resourcePath) {
        this.resourcePath = resourcePath;
    }
}
