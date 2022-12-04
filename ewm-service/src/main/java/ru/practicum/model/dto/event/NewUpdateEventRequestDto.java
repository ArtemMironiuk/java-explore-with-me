package ru.practicum.model.dto.event;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import ru.practicum.model.Location;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class NewUpdateEventRequestDto {
    private String annotation;
    private Integer category;
    private String description;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime eventDate;
    @PositiveOrZero
    @NotNull
    private Long eventId;
    private Location location;
    private Boolean paid;
    private Integer participantLimit;
    private String title;
}
