package ru.practicum.model.dto.compilation;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class NewCompilationDto {
    private Set<Long> events;
    private Boolean pinned;
    @NotNull
    @NotBlank
    private String title;
}
