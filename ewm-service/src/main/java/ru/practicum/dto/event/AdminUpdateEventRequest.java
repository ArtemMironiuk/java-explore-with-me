package ru.practicum.dto.event;

import lombok.*;
import ru.practicum.model.Location;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class AdminUpdateEventRequest {
    private String annotation;
    private Integer category;
    private String description;
    private LocalDateTime eventDate;
    private Location location;
    private Boolean paid;
    private Integer participantLimit;
    private Boolean requestModeration;
    private String title;

}
