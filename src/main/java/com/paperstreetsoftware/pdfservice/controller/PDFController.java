package com.paperstreetsoftware.pdfservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paperstreetsoftware.pdfservice.PDFRequest;
import com.paperstreetsoftware.pdfservice.PDFType;
import com.paperstreetsoftware.pdfservice.service.PDFGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class PDFController {

    private ObjectMapper objectMapper;
    private PDFGenerator pdfGenerator;
    private Validator validator;

    @Autowired
    public PDFController(ObjectMapper objectMapper, PDFGenerator pdfGenerator, Validator validator) {
        this.objectMapper = objectMapper;
        this.pdfGenerator = pdfGenerator;
        this.validator = validator;
    }

    @PostMapping("/api/v1/generatePDF")
    @ResponseBody
    public ResponseEntity<byte[]> generatePDF(@RequestParam PDFType pdfType, @RequestBody Map<String, Object> data) {
        Object model = objectMapper.convertValue(data, pdfType.getBeanType());

        Set<ConstraintViolation<Object>> constrains = validator.validate(model);
        if (!constrains.isEmpty()) {
            throw new ConstraintViolationException(String.format("Error occurred while validating the payload for PDF - %s.", pdfType), constrains);
        }

        byte[] pdf = pdfGenerator.createDocument(new PDFRequest.Builder()
                .setTemplateFileName(pdfType.getFileName())
                .addTemplateData("model", model)
                .build());

        return new ResponseEntity<>(pdf, buildHttpHeaders(pdfType), HttpStatus.OK);
    }

    private HttpHeaders buildHttpHeaders(PDFType pdfType) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.add("Access-Control-Allow-Origin", "*");
        headers.add("Access-Control-Allow-Methods", "POST");
        headers.add("Access-Control-Allow-Headers", "Content-Type");
        headers.add("Content-Disposition", "filename=" + pdfType.name().toLowerCase() + "-" + UUID.randomUUID() + ".pdf");
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        return headers;
    }

}
