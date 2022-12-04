package ru.practicum.model.dto.event;

import lombok.*;
import ru.practicum.model.dto.category.CategoryDto;
import ru.practicum.model.dto.user.UserShortDto;
import ru.practicum.model.Location;
import ru.practicum.model.StateEvent;

import javax.validation.constraints.*;

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
    private Integer confirmedRequests;
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
    private StateEvent state;
    @NotNull
    @NotBlank
    private String title;
    private Integer views;
}
