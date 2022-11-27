package ru.practicum.handler;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApiError {
    private List errors;
    private String message;
    private String reason;
    private HttpStatus status;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime timestamp;

    public ApiError(HttpStatus status, String reason, String message, LocalDateTime timestamp) {
        this.message = message;
        this.reason = reason;
        this.status = status;
        this.timestamp = timestamp;
    }

    public ApiError(final HttpStatus status, final String message, final String error, final String reason,
                    LocalDateTime timestamp) {
        super();
        this.status = status;
        this.message = message;
        errors = Arrays.asList(error);
        this.reason = reason;
        this.timestamp = timestamp;
    }

    public void setError(final String error) {
        errors = Arrays.asList(error);
    }
}
