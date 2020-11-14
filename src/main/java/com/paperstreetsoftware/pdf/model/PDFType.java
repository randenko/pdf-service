package com.paperstreetsoftware.pdf.model;

import com.paperstreetsoftware.pdf.domain.LeaseAgreementBean;
import com.paperstreetsoftware.pdf.domain.StandardPromissoryNoteBean;
import com.paperstreetsoftware.pdf.domain.TaxForm1099Bean;

public enum PDFType {

    LEASE_AGREEMENT("lease-agreement_en_US.ftlx", LeaseAgreementBean.class),
    STANDARD_PROMISSORY_NOTE("standard-promissory-note_en_US.ftlx", StandardPromissoryNoteBean.class),
    TAX_FORM_1099("tax-form-1099_en_US.ftlx", TaxForm1099Bean.class);

    private final String fileName;
    private final Class<?> beanType;

    PDFType(final String fileName, final Class<?> beanType) {
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
