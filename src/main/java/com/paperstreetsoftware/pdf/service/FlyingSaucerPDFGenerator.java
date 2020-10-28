package com.paperstreetsoftware.pdf.service;

import com.paperstreetsoftware.pdf.renderer.RenderingEngine;
import com.paperstreetsoftware.pdf.templating.TemplateEngine;

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
