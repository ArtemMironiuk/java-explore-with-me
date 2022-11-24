package ru.practicum.model.dto.compilation;

import lombok.*;
import ru.practicum.model.dto.event.EventShortDto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class CompilationDto {
    private List<EventShortDto> events;
    @NotNull
    @PositiveOrZero
    private Long id;
    @NotNull
    private Boolean pinned;
    @NotNull
    @NotBlank
    private String title;
}
