package com.paperstreetsoftware.pdfservice.templating.freemarker;

import com.paperstreetsoftware.pdfservice.templating.TemplateEngine;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.helper.W3CDom;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Entities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Component("freeMarkerTemplateEngine")
public class FreeMarkerTemplateEngine implements TemplateEngine {

    private final Configuration freeMarkerConfiguration;

    @Autowired
    public FreeMarkerTemplateEngine(Configuration freeMarkerConfiguration) {
        this.freeMarkerConfiguration = freeMarkerConfiguration;
    }

    @Override
    public org.w3c.dom.Document processTemplate(String templateName, Map<String, Object> templateData) {
        StringWriter writer = new StringWriter();
        try {
            Template template = freeMarkerConfiguration.getTemplate(templateName);
            template.process(templateData, writer);
            return convertHTMLToDocument(writer.toString());
        } catch (IOException | TemplateException e) {
            throw new RuntimeException("An error occurred while processing the FreeMarker template file.", e);
        }
    }

    private org.w3c.dom.Document convertHTMLToDocument(String html) {
        if (StringUtils.isEmpty(html)) {
            return null;
        }

        final Document document = Jsoup.parse(html);
        document.outputSettings().charset(StandardCharsets.UTF_8);
        document.outputSettings().syntax(Document.OutputSettings.Syntax.xml);
        document.outputSettings().escapeMode(Entities.EscapeMode.xhtml);
        document.outputSettings().prettyPrint(false);
        return (new W3CDom()).fromJsoup(document);
    }

}
