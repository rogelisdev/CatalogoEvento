package com.codeup.catalogoDeEventos.infrastructure.advice;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.List;

    @Schema(description = "Respuesta de error estándar de la API")
    public class ErrorResponse {

        @Schema(description = "Momento en que ocurrió el error", example = "2025-10-28T10:30:00")
        private LocalDateTime fecha;

        @Schema(description = "Código de estado HTTP", example = "404")
        private int status;

        @Schema(description = "Tipo de error", example = "Not Found")
        private String error;

        @Schema(description = "Mensaje descriptivo del error", example = "Evento con ID 999 no encontrado")
        private String message;

        @Schema(description = "Ruta del endpoint donde ocurrió el error", example = "/api/events/999")
        private String path;

        @Schema(description = "Lista de detalles adicionales del error (ej. validaciones)")
        private List<String> details;

        public ErrorResponse() {
            this.fecha = LocalDateTime.now();
        }

        public ErrorResponse(int status, String error, String message, String path) {
            this.fecha = LocalDateTime.now();
            this.status = status;
            this.error = error;
            this.message = message;
            this.path = path;
        }

        public ErrorResponse(int status, String error, String message, String path, List<String> details) {
            this.fecha = LocalDateTime.now();
            this.status = status;
            this.error = error;
            this.message = message;
            this.path = path;
            this.details = details;
        }

    }

