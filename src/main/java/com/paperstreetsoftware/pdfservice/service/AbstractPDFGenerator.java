package com.paperstreetsoftware.pdfservice.service;

import org.w3c.dom.Document;

import com.paperstreetsoftware.pdfservice.PDFRequest;
import com.paperstreetsoftware.pdfservice.renderer.RenderingEngine;
import com.paperstreetsoftware.pdfservice.templating.TemplateEngine;

public abstract class AbstractPDFGenerator implements PDFGenerator {
	
	TemplateEngine templateEngine;
	RenderingEngine renderingEngine;
	
	/**
	 * Sets the template engine.
	 * 
	 * @param templateEngine processes a HTML template file with user data.
	 */
	abstract void setTemplateEngine(TemplateEngine templateEngine);
	
	/**
	 * Sets the rendering engine.
	 * 
	 * @param renderingEngine
	 */
	abstract void setRenderingEngine(RenderingEngine renderingEngine);

	@Override
	public byte[] createDocument(PDFRequest pdfRequest) {
		Document doc = templateEngine.processTemplate(pdfRequest.getTemplateFileName(), pdfRequest.getTemplateData());
		return renderingEngine.renderPDF(doc);
	}

}
