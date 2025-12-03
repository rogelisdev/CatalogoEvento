package com.codeup.catalogoDeEventos.infrastructure.logging;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.UUID;

/**
 * Logging interceptor for HTTP requests and responses
 * Adds traceId to MDC for log correlation
 */
@Component
public class LoggingInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(LoggingInterceptor.class);
    private static final String TRACE_ID_KEY = "traceId";
    private static final String USER_KEY = "user";
    private static final String ENDPOINT_KEY = "endpoint";
    private static final String START_TIME_KEY = "startTime";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // Generate and set traceId
        String traceId = UUID.randomUUID().toString();
        MDC.put(TRACE_ID_KEY, traceId);

        // Set endpoint
        String endpoint = request.getMethod() + " " + request.getRequestURI();
        MDC.put(ENDPOINT_KEY, endpoint);

        // Set user (if available from authentication)
        String user = request.getRemoteUser();
        if (user != null) {
            MDC.put(USER_KEY, user);
        } else {
            MDC.put(USER_KEY, "anonymous");
        }

        // Store start time for duration calculation
        request.setAttribute(START_TIME_KEY, System.currentTimeMillis());

        // Add traceId to response header
        response.setHeader("X-Trace-Id", traceId);

        logger.info("Incoming request: {} from IP: {}", endpoint, request.getRemoteAddr());

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
            Exception ex) {
        try {
            Long startTime = (Long) request.getAttribute(START_TIME_KEY);
            long duration = System.currentTimeMillis() - startTime;

            String endpoint = request.getMethod() + " " + request.getRequestURI();
            int status = response.getStatus();

            if (status >= 500) {
                logger.error("Request completed: {} - Status: {} - Duration: {}ms", endpoint, status, duration);
            } else if (status >= 400) {
                logger.warn("Request completed: {} - Status: {} - Duration: {}ms", endpoint, status, duration);
            } else {
                logger.info("Request completed: {} - Status: {} - Duration: {}ms", endpoint, status, duration);
            }

            if (ex != null) {
                logger.error("Request failed with exception: {}", ex.getMessage(), ex);
            }
        } finally {
            // Clear MDC to prevent memory leaks
            MDC.clear();
        }
    }
}
