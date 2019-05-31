package com.paperstreetsoftware.pdfservice.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.paperstreetsoftware.pdfservice.renderer.RenderingEngine;
import com.paperstreetsoftware.pdfservice.templating.TemplateEngine;

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
