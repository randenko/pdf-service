package com.paperstreetsoftware.pdfservice.config.props;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("pdf")
public class PdfProperties {

    private String templatePath;
    private String resourcePath;

    public String getTemplatePath() {
        return templatePath;
    }

    public void setTemplatePath(String templatePath) {
        this.templatePath = templatePath;
    }

    public String getResourcePath() {
        return resourcePath;
    }

    public void setResourcePath(String resourcePath) {
        this.resourcePath = resourcePath;
    }
}
