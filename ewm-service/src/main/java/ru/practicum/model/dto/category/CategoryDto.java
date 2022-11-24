package ru.practicum.model.dto.category;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class CategoryDto {
    @PositiveOrZero
    private Long id;
    @NotBlank
    @NotNull
    private String name;
}
