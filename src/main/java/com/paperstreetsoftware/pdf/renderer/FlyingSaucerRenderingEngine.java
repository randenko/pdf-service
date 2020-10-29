package com.paperstreetsoftware.pdf.renderer;

import com.lowagie.text.DocumentException;
import com.paperstreetsoftware.pdf.config.props.PdfProperties;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.IOException;
import java.io.InputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.net.URL;

@Component("flyingSaucerRenderingEngine")
public class FlyingSaucerRenderingEngine implements RenderingEngine {

    private final ObjectFactory<ITextRenderer> rendererFactory;
    private final ResourceLoader resourceLoader;
    private final PdfProperties pdfProperties;

    @Autowired
    public FlyingSaucerRenderingEngine(ObjectFactory<ITextRenderer> rendererFactory, ResourceLoader resourceLoader,
            PdfProperties pdfProperties) {
        this.rendererFactory = rendererFactory;
        this.resourceLoader = resourceLoader;
        this.pdfProperties = pdfProperties;
    }

    @Override
    public InputStream renderPDF(Document doc) {
        try {
            ITextRenderer renderer = configureRenderer(doc);
            return pipeToInputStream(renderer);
        } catch (IOException e) {
            throw new RuntimeException("An error occurred while rendering the pdf file.", e);
        }
    }

    private ITextRenderer configureRenderer(Document doc) throws IOException {
        ITextRenderer renderer = rendererFactory.getObject();
        renderer.setDocument(doc, getBaseURLForResources());
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

    private String getBaseURLForResources() throws IOException {
        String basePath = null;
        URL url = resourceLoader.getClassLoader().getResource(pdfProperties.getResourcePath());
        if (url == null) {
            Resource resource = resourceLoader.getResource(pdfProperties.getResourcePath());
            if (resource.exists()) {
                url = resource.getURL();
            }
        }
        if (url != null) {
            basePath = url.toString();
        }
        return basePath;
    }

}
