package ru.practicum.model.dto.event;

import lombok.*;
import ru.practicum.model.Location;

import javax.validation.constraints.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class NewEventDto {
    @NotNull
    @Size(min = 20, max = 2000)
    private String annotation;
    @PositiveOrZero
    private Integer category;
    @NotNull
    @Size(min = 20, max = 7000)
    private String description;
    @NotNull
    private String eventDate;
    private Long eventId;
    @NotNull
    private Location location;
    private Boolean paid;
    private Integer participantLimit;
    private Boolean requestModeration;
    @NotNull
    @Size(min = 3, max = 120)
    private String title;
}
