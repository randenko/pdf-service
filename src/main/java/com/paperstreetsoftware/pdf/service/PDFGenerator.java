package com.paperstreetsoftware.pdf.service;

import java.io.InputStream;

import com.paperstreetsoftware.pdf.model.PDFRequest;

public interface PDFGenerator {

    /**
     * Creates a PDF from valid HTML.
     *
     * @param pdfRequest the request to be processed.
     * @return a byte array representation of a PDF.
     */
    InputStream createDocument(PDFRequest pdfRequest);

}
