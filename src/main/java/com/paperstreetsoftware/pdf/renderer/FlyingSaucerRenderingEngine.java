package com.paperstreetsoftware.pdf.renderer;

import com.lowagie.text.DocumentException;
import com.paperstreetsoftware.pdf.config.props.PdfProperties;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.IOException;
import java.io.InputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

@Component("flyingSaucerRenderingEngine")
public class FlyingSaucerRenderingEngine implements RenderingEngine {

    private final ObjectFactory<ITextRenderer> rendererFactory;
    private final PdfProperties pdfProperties;

    @Autowired
    public FlyingSaucerRenderingEngine(ObjectFactory<ITextRenderer> rendererFactory, PdfProperties pdfProperties) {
        this.rendererFactory = rendererFactory;
        this.pdfProperties = pdfProperties;
    }

    @Override
    public InputStream renderPDF(Document document) {
        try {
            ITextRenderer renderer = configureRenderer(document);
            return pipeToInputStream(renderer);
        } catch (IOException e) {
            throw new RuntimeException("An error occurred while rendering the pdf file.", e);
        }
    }

    private ITextRenderer configureRenderer(Document document) throws IOException {
        ITextRenderer renderer = rendererFactory.getObject();
        renderer.setDocument(document, getBaseURL());
        renderer.layout();
        return renderer;
    }

    private InputStream pipeToInputStream(ITextRenderer renderer) {
        PipedInputStream in = new PipedInputStream();
        new Thread(() -> {
            try (final PipedOutputStream out = new PipedOutputStream(in)) {
                renderer.createPDF(out);
            } catch (DocumentException | IOException e) {
                throw new RuntimeException(e);
            }
        }).start();
        return in;
    }

    private String getBaseURL() throws IOException {
        return pdfProperties.getResourcePath().getURL().toString() + "/";
    }

}
