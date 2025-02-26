package br.com.imaginer.resqueuevaccine.config;

import br.com.imaginer.resqueuevaccine.exception.ClinicAlreadyExistisException;
import br.com.imaginer.resqueuevaccine.exception.ClinicNotFoundException;
import br.com.imaginer.resqueuevaccine.exception.UnauthorizedUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(WebExchangeBindException.class)
  public ResponseEntity<Map<String, Object>> handleValidationException(WebExchangeBindException ex, ServerWebExchange exchange) {
    Map<String, String> fieldErrors = ex.getBindingResult().getFieldErrors()
        .stream()
        .collect(Collectors.toMap(
            FieldError::getField,
            error -> error.getDefaultMessage() != null ? error.getDefaultMessage() : "Erro desconhecido",
            (msg1, msg2) -> msg1
        ));

    log.warn("Erro de validação no endpoint {}: {}", exchange.getRequest().getPath(), fieldErrors);

    Map<String, Object> body = new HashMap<>();
    body.put("timestamp", LocalDateTime.now());
    body.put("status", HttpStatus.BAD_REQUEST.value());
    body.put("error", "Validation Error");
    body.put("message", "Erro de validação nos campos");
    body.put("errors", fieldErrors);
    body.put("path", exchange.getRequest().getPath().toString());

    return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(ResponseStatusException.class)
  public ResponseEntity<Map<String, Object>> handleResponseStatusException(ResponseStatusException ex, ServerWebExchange exchange) {
    return buildErrorResponse(HttpStatus.valueOf(ex.getStatusCode().value()), ex.getReason(), exchange);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<Map<String, Object>> handleGenericException(Exception ex, ServerWebExchange exchange) {
    String path = getRequestPath(exchange);
    log.error("Erro inesperado no endpoint {}: {}", path, ex.getMessage(), ex);
    return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Erro interno no servidor.", exchange);
  }

  @ExceptionHandler(ClinicAlreadyExistisException.class)
  public ResponseEntity<String> handleClinicAlreadyExistisException(ClinicAlreadyExistisException ex) {

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
  }

  @ExceptionHandler(ClinicNotFoundException.class)
  public ResponseEntity<String> handleClinicNotFoundException(ClinicNotFoundException ex) {

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
  }

  @ExceptionHandler(UnauthorizedUser.class)
  public ResponseEntity<String> handleUnauthorizedException(UnauthorizedUser ex) {
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
  }

  private ResponseEntity<Map<String, Object>> buildErrorResponse(HttpStatus status, String message, ServerWebExchange exchange) {
    Map<String, Object> body = new HashMap<>();
    body.put("timestamp", LocalDateTime.now());
    body.put("status", status.value());
    body.put("error", status.getReasonPhrase());
    body.put("message", message);
    body.put("path", getRequestPath(exchange));

    return new ResponseEntity<>(body, status);
  }

  private String getRequestPath(ServerWebExchange exchange) {
    return exchange.getRequest().getPath().toString();
  }
}
