package ru.practicum.model.dto.user;

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
public class UserShortDto {
    @NotBlank
    @NotNull
    @PositiveOrZero
    private Long id;
    @NotNull
    @NotBlank
    private String name;
}
