package com.paperstreetsoftware.pdf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.paperstreetsoftware.pdf.config.props.PdfProperties;
import com.paperstreetsoftware.pdf.config.props.SecurityProperties;

@SpringBootApplication
@EnableConfigurationProperties({SecurityProperties.class, PdfProperties.class})
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
