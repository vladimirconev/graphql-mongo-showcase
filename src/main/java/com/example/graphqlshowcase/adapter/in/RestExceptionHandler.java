package com.example.graphqlshowcase.adapter.in;

import com.example.graphqlshowcase.adapter.in.dto.response.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice(basePackageClasses = BookRestController.class)
public class RestExceptionHandler {

  private final DefaultErrorAttributes errorAttributes;

  public RestExceptionHandler(final DefaultErrorAttributes errorAttributes) {
    this.errorAttributes = errorAttributes;
  }

  protected ResponseEntity<ErrorResponse> handleException(
      final Exception exception,
      final HttpStatus status,
      final WebRequest request,
      final String message) {
    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.setContentType(MediaType.APPLICATION_JSON);
    ErrorResponse errorResponse = buildErrorResponseDto(status, exception, request, message);

    return new ResponseEntity<>(errorResponse, httpHeaders, status);
  }

  protected ErrorResponse buildErrorResponseDto(
      final HttpStatus httpStatus,
      final Throwable throwable,
      final WebRequest webRequest,
      final String messageDetails) {

    ServletWebRequest servletRequest = (ServletWebRequest) webRequest;
    HttpServletRequest request = servletRequest.getNativeRequest(HttpServletRequest.class);
    Objects.requireNonNull(request, "Http servlet request should not be null.");
    Map<String, Object> errors =
        errorAttributes.getErrorAttributes(webRequest, ErrorAttributeOptions.defaults());

    String status = httpStatus.getReasonPhrase();
    String code = String.valueOf(httpStatus.value());
    if (httpStatus == HttpStatus.INTERNAL_SERVER_ERROR) {
      status = HttpStatus.SERVICE_UNAVAILABLE.getReasonPhrase();
      code = String.valueOf(HttpStatus.SERVICE_UNAVAILABLE);
    }

    var message =
        Optional.ofNullable(messageDetails)
            .orElse(Optional.ofNullable(errors.get("message")).map(Object::toString).orElse(""));
    var path =
        Optional.ofNullable(errors.get("path"))
            .map(Object::toString)
            .orElse(request.getRequestURI());
    return new ErrorResponse(
        code,
        status,
        request.getMethod(),
        throwable.getClass().getSimpleName(),
        path,
        message,
        Instant.now());
  }

  @ResponseBody
  @ExceptionHandler(IllegalStateException.class)
  protected ResponseEntity<ErrorResponse> handleIllegalStateException(
      final IllegalStateException ex, final WebRequest webRequest) {

    return handleException(ex, HttpStatus.CONFLICT, webRequest, null);
  }

  @ResponseBody
  @ExceptionHandler(IllegalArgumentException.class)
  protected ResponseEntity<ErrorResponse> handleIllegalArgumentException(
      final IllegalArgumentException ex, final WebRequest webRequest) {

    return handleException(ex, HttpStatus.BAD_REQUEST, webRequest, null);
  }

  @ResponseBody
  @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
  protected ResponseEntity<ErrorResponse> handleHttpMediaTypeNotAcceptable(
      final HttpMediaTypeNotAcceptableException ex, final WebRequest request) {

    return handleException(
        ex,
        HttpStatus.NOT_ACCEPTABLE,
        request,
        "Use acceptable media types %s.".formatted(ex.getSupportedMediaTypes()));
  }

  @ResponseBody
  @ExceptionHandler(MissingServletRequestParameterException.class)
  protected ResponseEntity<ErrorResponse> handleMissingServletRequestParameterException(
      final MissingServletRequestParameterException ex, final WebRequest webRequest) {
    return handleException(ex, HttpStatus.BAD_REQUEST, webRequest, ex.getMessage());
  }

  @ResponseBody
  @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
  protected ResponseEntity<ErrorResponse> handleHttpRequestMethodNotSupported(
      final HttpRequestMethodNotSupportedException ex, final WebRequest request) {
    Set<HttpMethod> supportedMethods = ex.getSupportedHttpMethods();
    String detail = "Use a supported HTTP methods %s.".formatted(supportedMethods);
    return handleException(ex, HttpStatus.METHOD_NOT_ALLOWED, request, detail);
  }

  @ResponseBody
  @ExceptionHandler(NoSuchElementException.class)
  protected ResponseEntity<ErrorResponse> handleNoSuchElementException(
      final NoSuchElementException ex, final WebRequest webRequest) {
    return handleException(ex, HttpStatus.NOT_FOUND, webRequest, ex.getMessage());
  }

  @ResponseBody
  @ExceptionHandler(MethodArgumentNotValidException.class)
  protected ResponseEntity<ErrorResponse> handleNMethodArgumentNotValidException(
      final MethodArgumentNotValidException ex, final WebRequest webRequest) {
    var fieldErrors = ex.getFieldErrors();
    var message =
        fieldErrors.stream()
            .map(
                fieldError ->
                    "Validation failed for field '%s' with rejected value '%s' due to: '%s'"
                        .formatted(
                            fieldError.getField(),
                            fieldError.getRejectedValue(),
                            fieldError.getDefaultMessage()))
            .collect(Collectors.joining(System.lineSeparator()));
    var detailedMessage = Optional.of(message).orElse(ex.getMessage());
    return handleException(ex, HttpStatus.BAD_REQUEST, webRequest, detailedMessage);
  }

  @ResponseBody
  @ExceptionHandler(Exception.class)
  protected ResponseEntity<ErrorResponse> handleGenericException(
      final Exception ex, final WebRequest webRequest) {

    return handleException(ex, HttpStatus.SERVICE_UNAVAILABLE, webRequest, ex.getMessage());
  }
}
