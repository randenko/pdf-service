package com.paperstreetsoftware.pdf.renderer;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import com.paperstreetsoftware.pdf.config.props.PdfProperties;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;

import java.io.IOException;
import java.io.InputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

@Component("openHtmlToPdfRenderingEngine")
public class OpenHtmlToPdfRenderingEngine implements RenderingEngine {

    private final PdfProperties pdfProperties;

    public OpenHtmlToPdfRenderingEngine(final PdfProperties pdfProperties) {
        this.pdfProperties = pdfProperties;
    }

    @Override
    public InputStream renderPDF(Document doc) {
        PipedInputStream in = new PipedInputStream();
        new Thread(() -> {
            try (final PipedOutputStream out = new PipedOutputStream(in)) {
                PdfRendererBuilder builder = new PdfRendererBuilder();
                builder.useFastMode();
                builder.withW3cDocument(doc, getBaseURL());
                builder.toStream(out);
                builder.run();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).start();
        return in;
    }

    private String getBaseURL() throws IOException {
        return pdfProperties.getResourcePath().getURL().toString() + "/";
    }

}
