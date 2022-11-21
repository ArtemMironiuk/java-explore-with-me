package ru.practicum.dto.user;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class UserDto {
    @PositiveOrZero
    private Long id;
    @NotNull
    @NotBlank
    @Email
    private String email;
    @NotNull
    @NotBlank
    private String name;
}
