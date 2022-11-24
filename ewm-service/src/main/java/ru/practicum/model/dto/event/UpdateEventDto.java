package ru.practicum.model.dto.event;

import lombok.*;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class UpdateEventDto {
    @Size(min = 20, max = 2000)
    private String annotation;
    private Integer category;
    @Size(min = 20, max = 7000)
    private String description;
    @Future
    private LocalDateTime eventDate;
    @PositiveOrZero
    @NotNull
    private Long eventId;
    private Boolean paid;
    private Integer participantLimit;
    @Size(min = 3, max = 120)
    private String title;
}
