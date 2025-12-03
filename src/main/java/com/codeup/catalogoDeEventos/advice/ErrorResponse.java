package com.codeup.catalogoDeEventos.advice;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Standard API error response structure")
public class ErrorResponse {

    @Schema(description = "Time when the error occurred", example = "2025-10-28T10:30:00")
    private LocalDateTime timestamp; // Renamed from fecha

    @Schema(description = "HTTP status code", example = "404")
    private int status;

    @Schema(description = "Error type", example = "Not Found")
    private String error;

    @Schema(description = "Descriptive error message", example = "Event with ID 999 not found")
    private String message;

    @Schema(description = "Endpoint path where the error occurred", example = "/api/events/999")
    private String path;

    @Schema(description = "List of additional error details (e.g., field validations)")
    private List<String> details;

    /**
     * Helper constructor to set the timestamp automatically.
     * This will be used when manually building responses in the exception handler.
     */
    public ErrorResponse(int status, String error, String message, String path) {
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
    }
}