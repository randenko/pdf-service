package com.paperstreetsoftware.pdfservice.templating.freemarker.formatter;

import java.util.Locale;

import org.springframework.stereotype.Component;

import freemarker.core.Environment;
import freemarker.core.TemplateFormatUtil;
import freemarker.core.TemplateNumberFormat;
import freemarker.core.TemplateNumberFormatFactory;
import freemarker.core.TemplateValueFormatException;
import freemarker.template.TemplateModelException;
import freemarker.template.TemplateNumberModel;

@Component
public class OrdinalTemplateNumberFormatFactory extends TemplateNumberFormatFactory {

	public static final String[] PAYMENT_DAY_NAMES = {
			"", "First", "Second", "Third", "Forth", "Fifth", "Sixth"," Seventh", " Eighth", " Nineth",
			"Tenth", "Eleventh", "Twelveth", "Thirteenth","Fourteenth", "Fifteenth", "Sixteenth", "Seventeenth", "Eighteenth", "Nineteenth",
			"Twentieth", "Twenty First", "Twenty Second", "Twenty Third", "Twenty Forth", "Twenty Fifth", "Twenty Sixth", "Twenty Seventh", "Twenty Eighth", "Twenty Nineth",
			"Thirtieth", "Thirty First"};
	
	public static final String[] PAYMENT_DAY_SUFFIXES = {
           "  ", "st", "nd", "rd", "th", "th", "th", "th", "th", "th",
	       "th", "th", "th", "th", "th", "th", "th", "th", "th", "th",
	       "th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th",
	       "th", "st" };

	@Override
	public TemplateNumberFormat get(String params, Locale locale, Environment env) throws TemplateValueFormatException {
		return OrdinalTemplateNumberFormat.INSTANCE;
	}

	private static class OrdinalTemplateNumberFormat extends TemplateNumberFormat {

		private static final OrdinalTemplateNumberFormat INSTANCE = new OrdinalTemplateNumberFormat();

		private OrdinalTemplateNumberFormat() {
		}

		@Override
		public String formatToPlainText(TemplateNumberModel numberModel) throws TemplateValueFormatException, TemplateModelException {
			int i = TemplateFormatUtil.getNonNullNumber(numberModel).intValue();
			return PAYMENT_DAY_NAMES[i] + " (" + i + "<sup>" + PAYMENT_DAY_SUFFIXES[i] + "</sup>)";
		}

		@Override
		public boolean isLocaleBound() {
			return false;
		}

		@Override
		public String getDescription() {
			return "ordinal and number with superscript. example: 'Fifteenth (15<sup>th</sup>)'";
		}

	}

}
