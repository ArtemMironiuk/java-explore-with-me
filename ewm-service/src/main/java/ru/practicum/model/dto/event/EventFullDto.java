package ru.practicum.model.dto.event;

import lombok.*;
import ru.practicum.model.dto.category.CategoryDto;
import ru.practicum.model.dto.user.UserShortDto;
import ru.practicum.model.Location;
import ru.practicum.model.State;

import javax.validation.constraints.*;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class EventFullDto {
    @NotNull
    @NotBlank
    private String annotation;
    @NotNull
    @NotEmpty
    private CategoryDto category;
    private Integer confirmedRequest;
    private String createdOn;
    private String description;
    @NotNull
    private String eventDate;
    private Long id;
    @NotNull
    @NotEmpty
    private UserShortDto initiator;
    @NotNull
    @NotEmpty
    private Location location;
    @NotNull
    private Boolean paid;
    private Integer participantLimit;
    private String publishedOn;
    private Boolean requestModeration;
    private State state;
    @NotNull
    @NotBlank
    private String title;
    private Integer views;
}
