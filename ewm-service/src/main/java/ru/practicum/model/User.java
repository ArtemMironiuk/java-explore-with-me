package ru.practicum.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    @PositiveOrZero
    private Long id;
    @Column(name = "name", nullable = false)
    @NotNull
    private String name;
    @Column(name = "email", nullable = false, unique = true, length = 512)
    @Size(min = 1, max = 512)
    @Email
    @NotNull
    private String email;
}
