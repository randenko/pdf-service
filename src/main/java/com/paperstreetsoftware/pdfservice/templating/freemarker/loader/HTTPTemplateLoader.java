package com.paperstreetsoftware.pdfservice.templating.freemarker.loader;

import java.net.MalformedURLException;
import java.net.URL;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import freemarker.cache.URLTemplateLoader;

@Component
public class HTTPTemplateLoader extends URLTemplateLoader {
	
	@Value("${pdf.template.endpoint}")
	private String pdfTemplateEndpoint;
	
	@Value("${pdf.template.path}")
	private String templatePath;

	@Override
	protected URL getURL(String templateName) {
		String pdfTemplateEndpointFullPath = pdfTemplateEndpoint + templatePath + templateName;
		try {
			return new URL(pdfTemplateEndpointFullPath);
		} catch (MalformedURLException e) {
			throw new RuntimeException(String.format("An error occurred while trying to construct a URL from string '%s'.", pdfTemplateEndpointFullPath), e);
		}
	}

}
