package com.paperstreetsoftware.pdfservice.service;

import com.paperstreetsoftware.pdfservice.PDFRequest;

public interface PDFGenerator {
	
	/**
	 * Creates a PDF from valid HTML.
	 * 
	 * @param pdfRequest the request to be processed.
	 * @return a byte array representation of a PDF.
	 */
	byte[] createDocument(PDFRequest pdfRequest);

}
