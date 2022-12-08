package ru.practicum.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;

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
    private Long id;
    @Column(name = "name", nullable = false, length = 50)
    private String name;
    @Column(name = "email", nullable = false, unique = true, length = 75)
    @Email
    private String email;
}
