package ru.practicum.dto.user;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class NewUserRequest {
    @Email(message = "Неверно указан email")
    @NotBlank(message = "Поле email не должно быть пустым")
    private String email;
    @NotBlank(message = "Поле name не должно быть пустым")
    private String name;
}
