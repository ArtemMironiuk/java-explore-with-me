package ru.practicum.model.dto.event;

import lombok.*;
import ru.practicum.model.dto.category.CategoryDto;
import ru.practicum.model.dto.user.UserShortDto;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class EventShortDto {
    @NotNull
    @NotBlank
    private String annotation;
    @NotNull
    private CategoryDto category;
    private Integer confirmedRequests;
    @NotNull
    @NotBlank
    @Future
    private LocalDateTime eventDate;
    private Long id;
    @NotBlank
    private UserShortDto initiator;
    @NotNull
    private Boolean paid;
    @NotNull
    @NotBlank
    private String title;
    private Integer views;
}
