package com.paperstreetsoftware.pdfservice.templating;

import java.util.Map;

import org.w3c.dom.Document;

public interface TemplateEngine {
	
	/**
	 * Template engine is responsible to generate a w3c Document output based on template and changing data.
	 * 
	 * @param templateName name of the HTML template file that will be processed.
	 * @param templateData a map of changing data that populates into the template file. May be <code>null</code>.
	 * @return valid Document generated from a template and data.
	 */
	Document processTemplate(String templateName, Map<String, Object> templateData);

}
