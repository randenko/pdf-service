package com.paperstreetsoftware.pdfservice.model;

import static com.paperstreetsoftware.pdfservice.AppConfig.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonFormat;

public class StandardPromissoryNoteBean implements Serializable {
	private static final long serialVersionUID = -641373217323877663L;

	@NotBlank
	@Pattern(regexp = ASCII2_REGEX)
	private String lenderFirstName;

	@NotBlank
	@Pattern(regexp = ASCII2_REGEX)
	private String lenderLastName;

	@Pattern(regexp = ASCII2_REGEX)
	private String lenderMailingAddressLine1;

	@Pattern(regexp = ASCII2_REGEX)
	private String lenderMailingAddressLine2;

	@Pattern(regexp = ASCII2_REGEX)
	private String lenderMailingCity;

	@Pattern(regexp = ASCII2_REGEX)
	private String lenderMailingState;

	@Pattern(regexp = ZIP_REGEX)
	private String lenderMailingZip;

	@NotBlank
	@Pattern(regexp = ASCII2_REGEX)
	private String borrowerFirstName;

	@NotBlank
	@Pattern(regexp = ASCII2_REGEX)
	private String borrowerLastName;

	@Pattern(regexp = ASCII2_REGEX)
	private String borrowerMailingAddressLine1;

	@Pattern(regexp = ASCII2_REGEX)
	private String borrowerMailingAddressLine2;

	@Pattern(regexp = ASCII2_REGEX)
	private String borrowerMailingCity;

	@Pattern(regexp = ASCII2_REGEX)
	private String borrowerMailingState;

	@Pattern(regexp = ZIP_REGEX)
	private String borrowerMailingZip;

	@Pattern(regexp = ASCII2_REGEX)
	private String cosignerFirstName;

	@Pattern(regexp = ASCII2_REGEX)
	private String cosignerLastName;

	@NotNull
	private LoanType loanType; // secured or unsecured.

	@Min(6) // read value for min value from external source.
	@Max(48) // read value for max value from external source.
	private Long loanTerm; // in months.

	@DecimalMin("500.00") // read value for min value from external source.
	@DecimalMax("10000.00") // read value for max value from external source.
	private BigDecimal loanAmount; // in US dollars.

	@DecimalMin("2.00") // read value for min value from external source.
	@DecimalMax("12.00") // read value for max value from external source.
	private BigDecimal interestRate; // in percent.

	@DecimalMin("20.00") // read value for min value from external source.
	@DecimalMax("35.00") // read value for max value from external source.
	private BigDecimal lateFeeAmount; // in US dollars.

	@Min(1) // read value for min value from external source.
	@Max(31) // read value for max value from external source.
	private Long monthlyDueDay; // day of the month.

	private List<@Pattern(regexp = ASCII2_REGEX) String> securities;

	@NotNull
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_FORMAT)
	private LocalDate borrowDate;

	@NotNull
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_FORMAT)
	private LocalDate dueDate;

	@NotBlank
	@Pattern(regexp = ASCII2_REGEX)
	private String stateSigned;

	// @NotNull
	private byte[] lenderElectronicSignature;

	// @NotBlank
	@Pattern(regexp = ASCII2_REGEX)
	private String lenderSignatureBy;

	// @NotNull
	private byte[] borrowerElectronicSignature;

	// @NotBlank
	@Pattern(regexp = ASCII2_REGEX)
	private String borrowerSignatureBy;

	private byte[] cosignerElectronicSignature;

	@Pattern(regexp = ASCII2_REGEX)
	private String cosignerSignatureBy;

	public String getLenderFirstName() {
		return lenderFirstName;
	}

	public void setLenderFirstName(String lenderFirstName) {
		this.lenderFirstName = lenderFirstName;
	}

	public String getLenderLastName() {
		return lenderLastName;
	}

	public void setLenderLastName(String lenderLastName) {
		this.lenderLastName = lenderLastName;
	}

	public String getLenderMailingAddressLine1() {
		return lenderMailingAddressLine1;
	}

	public void setLenderMailingAddressLine1(String lenderMailingAddressLine1) {
		this.lenderMailingAddressLine1 = lenderMailingAddressLine1;
	}

	public String getLenderMailingAddressLine2() {
		return lenderMailingAddressLine2;
	}
	
	public void setLenderMailingAddressLine2(String lenderMailingAddressLine2) {
		this.lenderMailingAddressLine2 = lenderMailingAddressLine2;
	}
	
	public String getLenderMailingCity() {
		return lenderMailingCity;
	}
	
	public void setLenderMailingCity(String lenderMailingCity) {
		this.lenderMailingCity = lenderMailingCity;
	}
	
	public String getLenderMailingState() {
		return lenderMailingState;
	}
	
	public void setLenderMailingState(String lenderMailingState) {
		this.lenderMailingState = lenderMailingState;
	}
	
	public String getLenderMailingZip() {
		return lenderMailingZip;
	}
	
	public void setLenderMailingZip(String lenderMailingZip) {
		this.lenderMailingZip = lenderMailingZip;
	}
	
	public String getBorrowerFirstName() {
		return borrowerFirstName;
	}
	
	public void setBorrowerFirstName(String borrowerFirstName) {
		this.borrowerFirstName = borrowerFirstName;
	}
	
	public String getBorrowerLastName() {
		return borrowerLastName;
	}
	
	public void setBorrowerLastName(String borrowerLastName) {
		this.borrowerLastName = borrowerLastName;
	}
	
	public String getBorrowerMailingAddressLine1() {
		return borrowerMailingAddressLine1;
	}
	
	public void setBorrowerMailingAddressLine1(String borrowerMailingAddressLine1) {
		this.borrowerMailingAddressLine1 = borrowerMailingAddressLine1;
	}
	
	public String getBorrowerMailingAddressLine2() {
		return borrowerMailingAddressLine2;
	}
	
	public void setBorrowerMailingAddressLine2(String borrowerMailingAddressLine2) {
		this.borrowerMailingAddressLine2 = borrowerMailingAddressLine2;
	}
	
	public String getBorrowerMailingCity() {
		return borrowerMailingCity;
	}
	
	public void setBorrowerMailingCity(String borrowerMailingCity) {
		this.borrowerMailingCity = borrowerMailingCity;
	}
	
	public String getBorrowerMailingState() {
		return borrowerMailingState;
	}
	
	public void setBorrowerMailingState(String borrowerMailingState) {
		this.borrowerMailingState = borrowerMailingState;
	}
	
	public String getBorrowerMailingZip() {
		return borrowerMailingZip;
	}
	
	public void setBorrowerMailingZip(String borrowerMailingZip) {
		this.borrowerMailingZip = borrowerMailingZip;
	}
	
	public String getCosignerFirstName() {
		return cosignerFirstName;
	}
	
	public void setCosignerFirstName(String cosignerFirstName) {
		this.cosignerFirstName = cosignerFirstName;
	}
	
	public String getCosignerLastName() {
		return cosignerLastName;
	}
	
	public void setCosignerLastName(String cosignerLastName) {
		this.cosignerLastName = cosignerLastName;
	}
	
	public LoanType getLoanType() {
		return loanType;
	}
	
	public void setLoanType(LoanType loanType) {
		this.loanType = loanType;
	}
	
	public Long getLoanTerm() {
		return loanTerm;
	}
	
	public void setLoanTerm(Long loanTerm) {
		this.loanTerm = loanTerm;
	}
	
	public BigDecimal getLoanAmount() {
		return loanAmount;
	}
	
	public void setLoanAmount(BigDecimal loanAmount) {
		this.loanAmount = loanAmount;
	}
	
	public BigDecimal getInterestRate() {
		return interestRate;
	}
	
	public void setInterestRate(BigDecimal interestRate) {
		this.interestRate = interestRate;
	}
	
	public BigDecimal getLateFeeAmount() {
		return lateFeeAmount;
	}
	
	public void setLateFeeAmount(BigDecimal lateFeeAmount) {
		this.lateFeeAmount = lateFeeAmount;
	}
	
	public Long getMonthlyDueDay() {
		return monthlyDueDay;
	}
	
	public void setMonthlyDueDay(Long monthlyDueDay) {
		this.monthlyDueDay = monthlyDueDay;
	}
	
	public List<String> getSecurities() {
		return securities;
	}
	
	public void setSecurities(List<String> securities) {
		this.securities = securities;
	}
	
	public LocalDate getBorrowDate() {
		return borrowDate;
	}
	
	public void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}
	
	public LocalDate getDueDate() {
		return dueDate;
	}
	
	public void setBorrowDate(LocalDate borrowDate) {
		this.borrowDate = borrowDate;
	}
	
	public String getStateSigned() {
		return stateSigned;
	}
	
	public void setStateSigned(String stateSigned) {
		this.stateSigned = stateSigned;
	}
	
	public byte[] getLenderElectronicSignature() {
		return lenderElectronicSignature;
	}
	public void setLenderElectronicSignature(byte[] lenderElectronicSignature) {
		this.lenderElectronicSignature = lenderElectronicSignature;
	}
	
	public String getLenderSignatureBy() {
		return lenderSignatureBy;
	}
	
	public void setLenderSignatureBy(String lenderSignatureBy) {
		this.lenderSignatureBy = lenderSignatureBy;
	}
	
	public byte[] getBorrowerElectronicSignature() {
		return borrowerElectronicSignature;
	}
	
	public void setBorrowerElectronicSignature(byte[] borrowerElectronicSignature) {
		this.borrowerElectronicSignature = borrowerElectronicSignature;
	}
	
	public String getBorrowerSignatureBy() {
		return borrowerSignatureBy;
	}
	
	public void setBorrowerSignatureBy(String borrowerSignatureBy) {
		this.borrowerSignatureBy = borrowerSignatureBy;
	}
	
	public byte[] getCosignerElectronicSignature() {
		return cosignerElectronicSignature;
	}
	
	public void setCosignerElectronicSignature(byte[] cosignerElectronicSignature) {
		this.cosignerElectronicSignature = cosignerElectronicSignature;
	}
	
	public String getCosignerSignatureBy() {
		return cosignerSignatureBy;
	}
	
	public void setCosignerSignatureBy(String cosignerSignatureBy) {
		this.cosignerSignatureBy = cosignerSignatureBy;
	}
	
}
