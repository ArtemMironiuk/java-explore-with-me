package ru.practicum.model.dto.user;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class NewUserRequest {
    @NotNull
    @Email(message = "Неверно указан email")
    @NotBlank(message = "Поле email не должно быть пустым")
    private String email;
    @NotNull
    @NotBlank(message = "Поле name не должно быть пустым")
    private String name;
}
