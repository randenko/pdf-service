package com.paperstreetsoftware.pdfservice.templating.freemarker.formatter;

import static com.paperstreetsoftware.pdfservice.templating.freemarker.formatter.OrdinalTemplateNumberFormatFactory.PAYMENT_DAY_SUFFIXES;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import org.springframework.stereotype.Component;

import freemarker.core.Environment;
import freemarker.core.TemplateDateFormat;
import freemarker.core.TemplateDateFormatFactory;
import freemarker.core.TemplateFormatUtil;
import freemarker.core.TemplateValueFormatException;
import freemarker.template.TemplateDateModel;
import freemarker.template.TemplateModelException;

@Component
public class OrdinalTemplateDateFormatFactory extends TemplateDateFormatFactory {

	@Override
	public TemplateDateFormat get(String params, int dateType, Locale locale, TimeZone timeZone, boolean zonelessInput,
			Environment env) throws TemplateValueFormatException {
		return OrdinalTemplateDateFormat.INSTANCE;
	}

	private static class OrdinalTemplateDateFormat extends TemplateDateFormat {

		private static final OrdinalTemplateDateFormat INSTANCE = new OrdinalTemplateDateFormat();

		private OrdinalTemplateDateFormat() {
		}

		@Override
		public String formatToPlainText(TemplateDateModel dateModel) throws TemplateValueFormatException, TemplateModelException {
			Calendar cal = convertToCalendar(TemplateFormatUtil.getNonNullDate(dateModel));
			int i = cal.get(Calendar.DAY_OF_MONTH);
			DateFormat dateFormat = new SimpleDateFormat(" MMMM d'<sup>" + PAYMENT_DAY_SUFFIXES[i] + "</sup>' yyyy");
			return dateFormat.format(cal.getTime());
		}

		@Override
		public Object parse(String s, int dateType) throws TemplateValueFormatException {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean isLocaleBound() {
			return false;
		}

		@Override
		public boolean isTimeZoneBound() {
			return false;
		}

		@Override
		public String getDescription() {
			return "date containing ordinal superscript. example: 'May 1<sup>st</sup> 2019'";
		}

		private Calendar convertToCalendar(Date date) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			return cal;
		}
	}

}
