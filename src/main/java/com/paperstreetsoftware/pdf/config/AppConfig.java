package com.paperstreetsoftware.pdf.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.paperstreetsoftware.pdf.config.props.PdfProperties;
import com.paperstreetsoftware.pdf.config.props.SecurityProperties;

import freemarker.core.TemplateDateFormatFactory;
import freemarker.core.TemplateNumberFormatFactory;

import org.apache.commons.io.IOUtils;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.*;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import static freemarker.template.Configuration.VERSION_2_3_28;
import static freemarker.template.TemplateExceptionHandler.RETHROW_HANDLER;
import static java.util.Locale.US;
import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;
import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_SINGLETON;

@Configuration
@Import({ SecurityConfig.class })
public class AppConfig implements WebMvcConfigurer {

    public static final String DATE_FORMAT = "MM-dd-yyyy";
    public static final String DATE_TIME_FORMAT = "MM-dd-yyyy HH:mm:ss";
    public static final String ASCII2_REGEX = "^[\\x20-\\x7E]+$";
    public static final String ZIP_REGEX = "^\\d{5}(?:[-\\s]\\d{4})?$";

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("index.html");
    }

    @Bean
    @Scope(SCOPE_SINGLETON)
    public PublicKey publicKey(SecurityProperties securityProperties) throws NoSuchAlgorithmException,
            InvalidKeySpecException, IOException {
        try (InputStream inputStream = securityProperties.getPublicKey()) {
            KeyFactory kf = KeyFactory.getInstance("RSA");
            return kf.generatePublic(new X509EncodedKeySpec(IOUtils.toByteArray(inputStream)));
        }
    }

    @Bean
    @Scope(SCOPE_SINGLETON)
    public freemarker.template.Configuration freeMarkerConfiguration(ResourceLoader resourceLoader,
            PdfProperties pdfProperties, TemplateNumberFormatFactory templateNumberFormatFactory,
            TemplateDateFormatFactory templateDateFormatFactory) {
        freemarker.template.Configuration configuration = new freemarker.template.Configuration(VERSION_2_3_28);
        configuration.setDefaultEncoding(StandardCharsets.UTF_8.toString());
        configuration.setLocale(US);
        configuration.setClassLoaderForTemplateLoading(resourceLoader.getClassLoader(), pdfProperties.getTemplatePath());
        configuration.setTemplateExceptionHandler(RETHROW_HANDLER);
        configuration.setCustomNumberFormats(customNumberFormats(templateNumberFormatFactory));
        configuration.setCustomDateFormats(customDateFormats(templateDateFormatFactory));
        return configuration;
    }

    @Bean
    @Scope(SCOPE_SINGLETON)
    public Map<String, TemplateNumberFormatFactory> customNumberFormats(TemplateNumberFormatFactory templateNumberFormatFactory) {
        Map<String, TemplateNumberFormatFactory> customNumberFormats = new HashMap<>();
        customNumberFormats.put("ordinal", templateNumberFormatFactory);
        return customNumberFormats;
    }

    @Bean
    @Scope(SCOPE_SINGLETON)
    public Map<String, TemplateDateFormatFactory> customDateFormats(TemplateDateFormatFactory templateDateFormatFactory) {
        Map<String, TemplateDateFormatFactory> customDateFormats = new HashMap<>();
        customDateFormats.put("ordinalDate", templateDateFormatFactory);
        return customDateFormats;
    }

    @Bean
    @Scope(SCOPE_PROTOTYPE)
    public ITextRenderer renderer() {
        return new ITextRenderer();
    }

    @Bean
    @Scope(SCOPE_SINGLETON)
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new Jdk8Module());
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(MapperFeature.DEFAULT_VIEW_INCLUSION, true);
        return mapper;
    }

    @Bean
    @Scope(SCOPE_SINGLETON)
    public Jackson2ObjectMapperBuilderCustomizer jsonCustomizer() {
        return builder -> {
            builder.simpleDateFormat(DATE_TIME_FORMAT);
            builder.serializers(new LocalDateSerializer(DateTimeFormatter.ofPattern(DATE_FORMAT)));
            builder.serializers(new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(DATE_TIME_FORMAT)));
        };
    }

}
