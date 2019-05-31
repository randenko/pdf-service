package com.paperstreetsoftware.pdfservice.service;

import com.paperstreetsoftware.pdfservice.renderer.RenderingEngine;
import com.paperstreetsoftware.pdfservice.templating.TemplateEngine;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("flyingSaucerPDFGenerator")
public class FlyingSaucerPDFGenerator extends AbstractPDFGenerator {

    @Override
    @Resource(name = "freeMarkerTemplateEngine")
    void setTemplateEngine(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    @Override
    @Resource(name = "flyingSaucerRenderingEngine")
    void setRenderingEngine(RenderingEngine renderingEngine) {
        this.renderingEngine = renderingEngine;
    }

}
