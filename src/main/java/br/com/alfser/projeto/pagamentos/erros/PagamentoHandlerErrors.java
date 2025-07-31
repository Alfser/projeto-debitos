package br.com.alfser.projeto.pagamentos.erros;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.UnexpectedTypeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class PagamentoHandlerErrors {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(
            MethodArgumentNotValidException ex,
            HttpServletRequest request) {

        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.toList());

        ErrorResponse response = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                "Validation failed",
                request.getRequestURI(),
                errors
        );

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(PagamentoNotAvailableChangeException.class)
    public ResponseEntity<ErrorResponse> handlePagamentoNotAvailableChangeException(
            PagamentoNotAvailableChangeException ex,
            HttpServletRequest request) {

        ErrorResponse response = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                "Pagamento não pode ser alterado",
                request.getRequestURI(),
                List.of(ex.getMessage())
        );

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(PagamentoNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handlePagamentoNotFoundException(
            PagamentoNotFoundException ex,
            HttpServletRequest request) {

        return new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                "Pagamento não encontrado",
                request.getRequestURI(),
                List.of(ex.getMessage())
        );
    }

    @ExceptionHandler(InvalidPagamentoUpdateException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleInvalidPagamentoUpdateException(
            InvalidPagamentoUpdateException ex,
            HttpServletRequest request) {

        return new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                "Transição de status inválida",
                request.getRequestURI(),
                List.of(ex.getMessage())
        );
    }
}
