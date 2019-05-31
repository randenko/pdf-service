package com.paperstreetsoftware.pdfservice.exception;

import static com.paperstreetsoftware.pdfservice.AppConfig.DATE_TIME_FORMAT;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ErrorDetailResponse {

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_TIME_FORMAT)
	private final LocalDateTime timestamp;
	private final String message;
	private final String details;

	public ErrorDetailResponse(LocalDateTime timestamp, String message, String details) {
		this.timestamp = timestamp;
		this.message = message;
		this.details = details;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public String getMessage() {
		return message;
	}

	public String getDetails() {
		return details;
	}

}
