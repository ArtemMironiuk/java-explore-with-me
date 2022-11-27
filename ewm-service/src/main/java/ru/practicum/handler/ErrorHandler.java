package ru.practicum.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.practicum.handler.exception.ObjectNotFoundException;
import ru.practicum.handler.exception.ValidationException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
@Slf4j
public class ErrorHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Object> handleNotFound(ValidationException ex) {
        log.info("400 {}", ex.getMessage(), ex);
        ApiError error =  new ApiError(
                HttpStatus.BAD_REQUEST,
                "For the requested operation the conditions are not met.",
                ex.getMessage(),
                LocalDateTime.now());
        return ResponseEntityBuilder.build(error);
    }

    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<Object> handleNotFound(ObjectNotFoundException ex) {
        log.info("404 {}", ex.getMessage(), ex);
        ApiError error =  new ApiError(
                HttpStatus.NOT_FOUND,
                "The required object was not found.",
                ex.getMessage(),
                LocalDateTime.now());
        return ResponseEntityBuilder.build(error);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    protected ResponseEntity<Object> handleMethodArgumentTypeMismatch(
            MethodArgumentTypeMismatchException ex,
            WebRequest request) {

        List<String> details = new ArrayList<String>();
        details.add(ex.getMessage());

        ApiError err = new ApiError(
                details,
                "Type Mismatch",
                ex.getMessage(),
                HttpStatus.BAD_REQUEST,
                LocalDateTime.now());

        return ResponseEntityBuilder.build(err);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<?> handleConstraintViolationException(
            Exception ex,
            WebRequest request) {

        List<String> details = new ArrayList<String>();
        details.add(ex.getMessage());

        ApiError err = new ApiError(
                details,
                ex.getMessage(),
                "Integrity constraint has been violated",
                HttpStatus.CONFLICT,
                LocalDateTime.now());

        return ResponseEntityBuilder.build(err);
    }

//    @ExceptionHandler
//    @ResponseStatus(HttpStatus.NOT_FOUND)
//    public ErrorResponse handleNotFound(final IllegalArgumentException e) {
//        log.info("404 {}", e.getMessage(), e);
//        return new ErrorResponse(e.getMessage());
//    }

    @ExceptionHandler({ Throwable.class })
    public ResponseEntity<Object> handleAll(Throwable ex, WebRequest request) {

        List<String> details = new ArrayList<String>();
        details.add(ex.getLocalizedMessage());

        ApiError err = new ApiError(
                details,
                ex.getMessage(),
                "Error occurred",
                HttpStatus.INTERNAL_SERVER_ERROR,
                LocalDateTime.now());

        return ResponseEntityBuilder.build(err);

    }

//    @ExceptionHandler
//    @ResponseStatus(HttpStatus.CONFLICT)
//    public ErrorResponse handleNotFound(final ConflictException e) {
//        log.info("409 {}", e.getMessage(), e);
//        return new ErrorResponse(e.getMessage());
//    }
//
//    @ExceptionHandler
//    @ResponseStatus(HttpStatus.CONFLICT)
//    public ErrorResponse handleNotFound(final DataIntegrityViolationException e) {
//        log.info("409 {}", e.getMessage(), e);
//        return new ErrorResponse(e.getMessage());
//    }

//    @ExceptionHandler
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public ErrorResponse handleNotFound(final Throwable e) {
//        log.info("400 {}", e.getMessage(), e);
//        return new ErrorResponse(e.getClass().getName());
//    }
}
