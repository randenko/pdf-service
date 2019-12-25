package com.paperstreetsoftware.pdfservice;

import com.paperstreetsoftware.pdfservice.config.props.PdfProperties;
import com.paperstreetsoftware.pdfservice.config.props.SecurityProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({SecurityProperties.class, PdfProperties.class})
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
