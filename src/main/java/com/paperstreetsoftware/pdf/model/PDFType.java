package com.paperstreetsoftware.pdf.model;

import com.paperstreetsoftware.pdf.domain.StandardPromissoryNoteBean;
import com.paperstreetsoftware.pdf.domain.TaxForm1099Bean;

public enum PDFType {

    STANDARD_PROMISSORY_NOTE("standard-promissory-note_en_US.ftlh", StandardPromissoryNoteBean.class),
    TAX_FORM_1099("tax-form-1099_en_US.ftlh", TaxForm1099Bean.class);

    private String fileName;
    private Class<?> beanType;

    PDFType(String fileName, Class<?> beanType) {
        this.fileName = fileName;
        this.beanType = beanType;
    }

    public String getFileName() {
        return this.fileName;
    }

    public Class<?> getBeanType() {
        return this.beanType;
    }

}
