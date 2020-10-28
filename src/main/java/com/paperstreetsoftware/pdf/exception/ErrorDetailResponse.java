package com.paperstreetsoftware.pdf.exception;

import com.fasterxml.jackson.annotation.JsonFormat;

import static com.paperstreetsoftware.pdf.config.AppConfig.DATE_TIME_FORMAT;

import java.time.LocalDateTime;

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
