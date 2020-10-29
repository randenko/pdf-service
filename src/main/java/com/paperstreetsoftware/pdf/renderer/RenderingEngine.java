package com.paperstreetsoftware.pdf.renderer;

import java.io.InputStream;

import org.w3c.dom.Document;

public interface RenderingEngine {

    /**
     * Rendering engine is responsible for rendering a PDF from HTML
     *
     * @param doc valid w3c Document that will be rendered as a PDF.
     * @return a byte array representation of a PDF.
     */
    InputStream renderPDF(Document doc);

}
