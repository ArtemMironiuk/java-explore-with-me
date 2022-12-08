package ru.practicum.model.dto.category;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class NewCategoryDto {
    @NotBlank(message = "название категории не может быть пустым")
    private String name;
}
