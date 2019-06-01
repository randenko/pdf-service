package com.paperstreetsoftware.pdfservice.renderer;

import com.lowagie.text.DocumentException;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    private ObjectFactory<ITextRenderer> rendererFactory;
    private ResourceLoader resourceLoader;

    @Value("${pdf.template.endpoint}")
    private String pdfTemplateEndpoint;

    @Value("${pdf.resource.path}")
    private String resourcePath;

    @Autowired
    public FlyingSaucerRenderingEngine(ObjectFactory<ITextRenderer> rendererFactory, ResourceLoader resourceLoader) {
        this.rendererFactory = rendererFactory;
        this.resourceLoader = resourceLoader;
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
        URL url = resourceLoader.getClassLoader().getResource(resourcePath);
        if (url == null) {
            Resource resource = resourceLoader.getResource(resourcePath);
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
