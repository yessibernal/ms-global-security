package com.innter.msglobalsecurity.exceptions;

import com.innter.msglobalsecurity.model.dtos.response.ErrorResponse;
import io.jsonwebtoken.ClaimJwtException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.validation.FieldError;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static com.innter.msglobalsecurity.model.constants.UtilConstants.GENERAL_ERROR_CODE;


@Log4j2
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private ErrorResponse createErrorResponseException(Exception ex, HttpStatusCode status, Object data, WebRequest request) {
        String uuid = UUID.randomUUID().toString();
        log.error("GlobalExceptionHandler.createErrorResponseException");
        log.error(uuid);
        String path = ((ServletWebRequest)request).getRequest().getRequestURI();
        return ErrorResponse.builder()
                .code(String.format(GENERAL_ERROR_CODE,status.value()))
                .UUID(uuid)
                .timestamp(OffsetDateTime.now())
                .data(data)
                .path(path)
                .build();

    }

    @ExceptionHandler(ClaimJwtException.class)
    public final ResponseEntity<Object> handleClaimJwtException(ClaimJwtException ex, WebRequest request) {
        log.error("GlobalExceptionHandler.handleClaimJwtException");
        log.error(String.format("Error: %s", ex.getMessage()));
        HttpStatusCode status = HttpStatusCode.valueOf(HttpStatus.FORBIDDEN.value());
        return new ResponseEntity<>(createErrorResponseException(ex, status, null, request), status);
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
        log.error("GlobalExceptionHandler.handleAllExceptions");
        log.error(String.format("Error: %s", ex.getMessage()));
        HttpStatusCode status = HttpStatusCode.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value());
        return new ResponseEntity<>(createErrorResponseException(ex, status, null, request), status);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        log.error("GlobalExceptionHandler.handleMethodArgumentNotValid");
        log.error(String.format("Error: %s", ex.getMessage()));
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(createErrorResponseException(ex, status, errors, request), status);
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        log.error("GlobalExceptionHandler.handleNoHandlerFoundException");
        log.error(String.format("Error: %s", ex.getMessage()));
        return new ResponseEntity<>(createErrorResponseException(ex, status, null, request), status);
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        log.error("GlobalExceptionHandler.handleHttpRequestMethodNotSupported");
        log.error(String.format("Error: %s", ex.getMessage()));
        return new ResponseEntity<>(createErrorResponseException(ex, status, null, request), status);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        log.error("GlobalExceptionHandler.handleHttpMediaTypeNotSupported");
        log.error(String.format("Error: %s", ex.getMessage()));
        return new ResponseEntity<>(createErrorResponseException(ex, status, null, request), status);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        log.error("GlobalExceptionHandler.handleHttpMediaTypeNotAcceptable");
        log.error(String.format("Error: %s", ex.getMessage()));
        return new ResponseEntity<>(createErrorResponseException(ex, status, null, request), status);
    }

    @Override
    protected ResponseEntity<Object> handleMissingPathVariable(MissingPathVariableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        log.error("GlobalExceptionHandler.handleMissingPathVariable");
        log.error(String.format("Error: %s", ex.getMessage()));
        return new ResponseEntity<>(createErrorResponseException(ex, status, null, request), status);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        log.error("GlobalExceptionHandler.handleMissingServletRequestParameter");
        log.error(String.format("Error: %s", ex.getMessage()));
        return new ResponseEntity<>(createErrorResponseException(ex, status, null, request), status);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestPart(MissingServletRequestPartException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        log.error("GlobalExceptionHandler.handleMissingServletRequestPart");
        log.error(String.format("Error: %s", ex.getMessage()));
        return new ResponseEntity<>(createErrorResponseException(ex, status, null, request), status);
    }

    @Override
    protected ResponseEntity<Object> handleServletRequestBindingException(ServletRequestBindingException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        log.error("GlobalExceptionHandler.handleServletRequestBindingException");
        log.error(String.format("Error: %s", ex.getMessage()));
        return new ResponseEntity<>(createErrorResponseException(ex, status, null, request), status);
    }

    @Override
    protected ResponseEntity<Object> handleAsyncRequestTimeoutException(AsyncRequestTimeoutException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        log.error("GlobalExceptionHandler.handleAsyncRequestTimeoutException");
        log.error(String.format("Error: %s", ex.getMessage()));
        return new ResponseEntity<>(createErrorResponseException(ex, status, null, request), status);
    }

    @Override
    protected ResponseEntity<Object> handleErrorResponseException(ErrorResponseException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        log.error("GlobalExceptionHandler.handleErrorResponseException");
        log.error(String.format("Error: %s", ex.getMessage()));
        return new ResponseEntity<>(createErrorResponseException(ex, status, null, request), status);
    }

    @Override
    protected ResponseEntity<Object> handleConversionNotSupported(ConversionNotSupportedException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        log.error("GlobalExceptionHandler.handleConversionNotSupported");
        log.error(String.format("Error: %s", ex.getMessage()));
        return new ResponseEntity<>(createErrorResponseException(ex, status, null, request), status);
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        log.error("GlobalExceptionHandler.handleTypeMismatch");
        log.error(String.format("Error: %s", ex.getMessage()));
        return new ResponseEntity<>(createErrorResponseException(ex, status, null, request), status);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        log.error("GlobalExceptionHandler.handleHttpMessageNotReadable");
        log.error(String.format("Error: %s", ex.getMessage()));
        return new ResponseEntity<>(createErrorResponseException(ex, status, null, request), status);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotWritable(HttpMessageNotWritableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        log.error("GlobalExceptionHandler.handleHttpMessageNotWritable");
        log.error(String.format("Error: %s", ex.getMessage()));
        return new ResponseEntity<>(createErrorResponseException(ex, status, null, request), status);
    }
}
