package com.codeup.catalogoDeEventos.advice;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "Standard API error response")
public class ErrorResponse {

    @Schema(description = "Timestamp when the error occurred", example = "2025-10-28T10:30:00")
    private LocalDateTime date;

    @Schema(description = "HTTP status code", example = "404")
    private int status;

    @Schema(description = "Type of error", example = "Not Found")
    private String error;

    @Schema(description = "Descriptive error message", example = "Event with ID 999 not found")
    private String message;

    @Schema(description = "Endpoint path where the error occurred", example = "/api/events/999")
    private String path;

    @Schema(description = "List of additional error details (e.g., validations)")
    private List<String> details;

    public ErrorResponse() {
        this.date = LocalDateTime.now();
    }

    public ErrorResponse(int status, String error, String message, String path) {
        this.date = LocalDateTime.now();
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
    }

    public ErrorResponse(int status, String error, String message, String path, List<String> details) {
        this.date = LocalDateTime.now();
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
        this.details = details;
    }
}


