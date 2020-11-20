package com.paperstreetsoftware.pdf.service;

import com.paperstreetsoftware.pdf.renderer.RenderingEngine;
import com.paperstreetsoftware.pdf.templating.TemplateEngine;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("openHtmlPDFGenerator")
public class OpenHtmlGenerator extends AbstractPDFGenerator {

    @Override
    @Resource(name = "freeMarkerTemplateEngine")
    void setTemplateEngine(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    @Override
    @Resource(name = "openHtmlToPdfRenderingEngine")
    void setRenderingEngine(RenderingEngine renderingEngine) {
        this.renderingEngine = renderingEngine;
    }
}
