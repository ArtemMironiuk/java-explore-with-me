package ru.practicum.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.practicum.handler.exception.ExistsElementException;
import ru.practicum.handler.exception.ObjectNotFoundException;
import ru.practicum.handler.exception.ValidationException;

import javax.validation.ConstraintViolationException;

@RestControllerAdvice
@Slf4j
public class ErrorHandler extends ResponseEntityExceptionHandler {

    /**
     * Handle MissingServletRequestParameterException. Срабатывает, когда отсутствует «обязательный» параметр запроса.
     * Triggered when a 'required' request parameter is missing.
     */
    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(
            MissingServletRequestParameterException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request) {
        String error = ex.getParameterName() + " параметр отсутствует.";
        return ResponseEntityBuilder.build(new ApiError(HttpStatus.BAD_REQUEST, error, ex));
    }

    /**
     * Handle MethodArgumentNotValidException. Срабатывает, когда объект не проходит валидацию по аннотации @Valid.
     * Triggered when an object fails @Valid validation.
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST);
        apiError.setMessage("Ошибка валидации!");
        apiError.setReason("Проверьте все заполенные поля");
        apiError.addValidationErrors(ex.getBindingResult().getFieldErrors());
        apiError.addValidationError(ex.getBindingResult().getGlobalErrors());
        return ResponseEntityBuilder.build(apiError);
    }

    /**
     * Handle NoHandlerFoundException.
     */
    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(
            NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST);
        apiError.setMessage(String.format("Данного метода %s нет в URL %s", ex.getHttpMethod(), ex.getRequestURL()));
        apiError.setReason(ex.getMessage());
        return ResponseEntityBuilder.build(apiError);
    }

    @ExceptionHandler(ExistsElementException.class)
    public ResponseEntity<Object> handleExistsElementException(ExistsElementException ex) {
        log.error("409 {}", ex.getMessage());
        ApiError error = new ApiError(HttpStatus.CONFLICT);
        error.setMessage(ex.getMessage());
        error.setReason("Повторяющееся поле.");
        return ResponseEntityBuilder.build(error);
    }

    /**
     * Handles javax.validation.ConstraintViolationException. Сработает, когда @Validated. Thrown when @Validated fails.
     */
    @ExceptionHandler(javax.validation.ConstraintViolationException.class)
    protected ResponseEntity<Object> handleConstraintViolation(
            javax.validation.ConstraintViolationException ex) {
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST);
        apiError.setMessage("Ошибка валидации!");
        apiError.addValidationErrors(ex.getConstraintViolations());
        return ResponseEntityBuilder.build(apiError);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Object> handleNotFound(ValidationException ex) {
        log.info("404 {}", ex.getMessage(), ex);
        ApiError error = new ApiError(HttpStatus.BAD_REQUEST);
        error.setMessage(ex.getMessage());
        error.setReason("Условия не выполнены.");
        return ResponseEntityBuilder.build(error);
    }

    /**
     * ObjectNotFoundException сработает, когда необходимый объект не будет найден.
     */
    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<Object> handleNotFound(ObjectNotFoundException ex) {
        log.info("404 {}", ex.getMessage(), ex);
        ApiError error = new ApiError(HttpStatus.NOT_FOUND);
        error.setMessage(ex.getMessage());
        error.setReason("Необходимый объект не найден.");
        return ResponseEntityBuilder.build(error);
    }

    /**
     * Handle DataIntegrityViolationException, inspects the cause for different DB causes.
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    protected ResponseEntity<Object> handleDataIntegrityViolation(DataIntegrityViolationException ex,
                                                                  WebRequest request) {
        if (ex.getCause() instanceof ConstraintViolationException) {
            return ResponseEntityBuilder.build(new ApiError(HttpStatus.CONFLICT, "Database error", ex.getCause()));
        }
        return ResponseEntityBuilder.build(new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, ex));
    }

    /**
     * * Handle Exception, handle generic Exception.class. Обработчик общего Exception.class.
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    protected ResponseEntity<Object> handleMethodArgumentTypeMismatch(
            MethodArgumentTypeMismatchException ex,
            WebRequest request) {

        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST);
        apiError.setMessage(String.format("Параметр '%s' со значением '%s' не может быть преобразован в тип '%s'",
                ex.getName(), ex.getValue(), ex.getRequiredType().getSimpleName()));
        apiError.setReason(ex.getClass().getName());
        return ResponseEntityBuilder.build(apiError);
    }

}
