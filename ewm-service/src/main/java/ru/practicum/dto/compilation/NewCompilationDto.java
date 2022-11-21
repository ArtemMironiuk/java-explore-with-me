package ru.practicum.dto.compilation;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class NewCompilationDto {
    private List<Long> events;
    private Boolean pinned;
    @NotNull
    @NotBlank
    private String title;
}
