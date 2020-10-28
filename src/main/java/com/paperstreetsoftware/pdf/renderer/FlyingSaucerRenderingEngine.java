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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
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
    public byte[] renderPDF(Document doc) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        try {
            ITextRenderer renderer = rendererFactory.getObject();
            renderer.setDocument(doc, getBaseURLForResources());
            renderer.layout();
            renderer.createPDF(byteArrayOutputStream);
        } catch (IOException | DocumentException e) {
            throw new RuntimeException("An error occurred while rendering the pdf file.", e);
        }
        return byteArrayOutputStream.toByteArray();
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
