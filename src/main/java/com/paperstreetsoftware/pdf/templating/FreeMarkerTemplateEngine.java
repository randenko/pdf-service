package com.paperstreetsoftware.pdf.templating;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import java.io.*;
import java.util.Map;

@Component("freeMarkerTemplateEngine")
public class FreeMarkerTemplateEngine implements TemplateEngine {

    private final Configuration freeMarkerConfiguration;

    @Autowired
    public FreeMarkerTemplateEngine(Configuration freeMarkerConfiguration) {
        this.freeMarkerConfiguration = freeMarkerConfiguration;
    }

    @Override
    public Document processTemplate(String templateName, Map<String, Object> templateData) {
        try (Reader reader = pipeToReader(templateName, templateData)) {
            return convertHTMLToDocument(reader);
        } catch (TransformerConfigurationException | ParserConfigurationException | IOException | SAXException e) {
            throw new RuntimeException("An error occurred while processing the FreeMarker template file.", e);
        }
    }

    private Reader pipeToReader(String templateName, Map<String, Object> templateData) {
        PipedReader reader = new PipedReader();
        new Thread(() -> {
            try (final PipedWriter writer = new PipedWriter(reader)) {
                Template template = freeMarkerConfiguration.getTemplate(templateName);
                template.process(templateData, writer);
            } catch (IOException | TemplateException e) {
                throw new RuntimeException(e);
            }
        }).start();
        return reader;
    }

    private Document convertHTMLToDocument(Reader reader) throws TransformerConfigurationException, ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new InputSource(reader));

        DOMSource source = new DOMSource(document);
        TransformerFactory transformerFactory = TransformerFactory.newDefaultInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
        transformer.setOutputProperty(OutputKeys.METHOD, "xml");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        //transformer.transform(source, result);
        return document;
    }

}
