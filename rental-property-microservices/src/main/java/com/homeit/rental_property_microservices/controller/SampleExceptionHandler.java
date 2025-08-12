package com.homeit.rental_property_microservices.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;
import java.time.LocalDateTime;

@RestControllerAdvice
@Slf4j
public class SampleExceptionHandler {

    // ✅ 403 for method/authorization failures (@PreAuthorize, etc.)
    @ExceptionHandler({ AuthorizationDeniedException.class, AccessDeniedException.class })
    public ResponseEntity<ProblemDetail> handleAccessDenied(Exception ex) {
        log.error("access denied: ", ex);
        ProblemDetail pd = ProblemDetail.forStatus(HttpStatus.FORBIDDEN);
        pd.setTitle("Forbidden");
        pd.setDetail("Access is denied");
        pd.setInstance(URI.create("/api/v1/rental-properties/error"));
        pd.setProperty("timestamp", LocalDateTime.now().toString());
        return new ResponseEntity<>(pd, HttpStatus.FORBIDDEN);
    }

    // ✅ 401 for auth failures (optional, useful if you ever bubble AuthenticationException)
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ProblemDetail> handleAuth(AuthenticationException ex) {
        log.error("authentication error: ", ex);
        ProblemDetail pd = ProblemDetail.forStatus(HttpStatus.UNAUTHORIZED);
        pd.setTitle("Unauthorized");
        pd.setDetail(ex.getMessage());
        pd.setInstance(URI.create("/api/v1/rental-properties/error"));
        pd.setProperty("timestamp", LocalDateTime.now().toString());
        return new ResponseEntity<>(pd, HttpStatus.UNAUTHORIZED);
    }

    // ✅ true catch-all — everything else stays 500
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ProblemDetail> handleGenericException(RuntimeException ex) {
        log.error("exception: ", ex);
        ProblemDetail pd = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        pd.setTitle("Customized Internal Server Error");
        pd.setDetail("An unexpected error occurred: " + ex.getMessage());
        pd.setInstance(URI.create("/api/v1/rental-properties/error"));
        pd.setProperty("timestamp", LocalDateTime.now().toString());
        return new ResponseEntity<>(pd, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
