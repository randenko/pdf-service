package com.paperstreetsoftware.pdf.service;

import com.paperstreetsoftware.pdf.model.PDFRequest;
import com.paperstreetsoftware.pdf.renderer.RenderingEngine;
import com.paperstreetsoftware.pdf.templating.TemplateEngine;

import java.io.InputStream;

import org.w3c.dom.Document;

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
    public InputStream createDocument(PDFRequest pdfRequest) {
        Document doc = templateEngine.processTemplate(pdfRequest.getTemplateFileName(), pdfRequest.getTemplateData());
        return renderingEngine.renderPDF(doc);
    }

}
