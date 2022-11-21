package ru.practicum.dto.compilation;

import lombok.*;
import ru.practicum.dto.event.EventShortDto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class CompilationDto {
    private EventShortDto events;
    @NotNull
    @PositiveOrZero
    private Long id;
    @NotNull
    private Boolean pinned;
    @NotNull
    @NotBlank
    private String title;
}
