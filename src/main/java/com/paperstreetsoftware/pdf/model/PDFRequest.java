package com.paperstreetsoftware.pdf.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class PDFRequest implements Serializable {
    private static final long serialVersionUID = 1198122592741147784L;

    private String templateFileName;
    private Map<String, Object> templateData;

    public String getTemplateFileName() {
        return templateFileName;
    }

    public Map<String, Object> getTemplateData() {
        return templateData;
    }

    public static class Builder {
        PDFRequest pdfRequest = new PDFRequest();

        public Builder setTemplateFileName(String templateFileName) {
            pdfRequest.templateFileName = templateFileName;
            return this;
        }

        public Builder addTemplateData(String key, Object model) {
            if (pdfRequest.templateData == null) {
                pdfRequest.templateData = new HashMap<>();
            }
            pdfRequest.templateData.put(key, model);
            return this;
        }

        public PDFRequest build() {
            if (pdfRequest.templateFileName == null) {
                throw new IllegalArgumentException("An error occurred while building a PDFRequest object: templateName must be set.");
            }
            return pdfRequest;
        }
    }

}
