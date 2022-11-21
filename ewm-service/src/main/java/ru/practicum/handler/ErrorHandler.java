package ru.practicum.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.handler.exception.ObjectNotFoundException;
import ru.practicum.handler.exception.ValidationException;

import java.time.LocalDateTime;
import java.util.List;

@RestControllerAdvice
@Slf4j
public class ErrorHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleNotFound(final ValidationException e) {
        log.info("400 {}", e.getMessage(), e);
        return new ErrorResponse(e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handleNotFound(final ObjectNotFoundException e) {
        log.info("404 {}", e.getMessage(), e);
        return new ApiError(e.printStackTrace() ,e.getMessage(), e.getCause(), HttpStatus.NOT_FOUND.toString(),
                LocalDateTime.now().withNano(0));
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFound(final IllegalArgumentException e) {
        log.info("404 {}", e.getMessage(), e);
        return new ErrorResponse(e.getMessage());
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

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleNotFound(final Throwable e) {
        log.info("400 {}", e.getMessage(), e);
        return new ErrorResponse(e.getClass().getName());
    }
}
