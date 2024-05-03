package com.pdg.pymesbackend.error;

import com.pdg.pymesbackend.model.enums.InfoError;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(PymeException.class)
    public ResponseEntity<PymeApplicationError> handlePymeException(PymeException ex) {
        PymeApplicationError body = PymeApplicationError.builder()
                .code(ex.getPymeExceptionType().getCode()+"")
                .message(ex.getPymeExceptionType().getMessage())
                .status(ex.getPymeExceptionType().getResponseStatus())
                .time(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(body, body.getStatus());
    }

    @Override
    protected ResponseEntity<Object> handleMissingPathVariable
            (MissingPathVariableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        PymeApplicationError body = PymeApplicationError.builder()
                .code(InfoError.MISSING_PATH_VARIABLE.getCode())
                .message(ex.getVariableName() + InfoError.MISSING_PATH_VARIABLE.getMessage())
                .status(HttpStatus.BAD_REQUEST)
                .time(LocalDateTime.now())
                .build();

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid
            (MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        PymeApplicationError error = PymeApplicationErrorDetail.builder()
                .code(InfoError.ARGUMENT_NOT_VALID.getCode())
                .message(InfoError.ARGUMENT_NOT_VALID.getMessage())
                .status(HttpStatus.BAD_REQUEST)
                .time(LocalDateTime.now())
                .detail(formatDetailMessage(ex.getFieldErrors()))
                .build();
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    private static String formatDetailMessage(List<FieldError> errors) {
        StringBuilder detailBuilder = new StringBuilder();
        if (!errors.isEmpty()) {
            for (int i = 0; i < errors.size(); i++) {
                detailBuilder.append("Error ").append(i + 1).append(": {").append(errors.get(i).getDefaultMessage()).append("} ");
            }
        }
        return detailBuilder.toString();
    }



}
