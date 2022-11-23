package ru.practicum.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "compilations")
public class Compilation {

    @NotNull
    @OneToMany
    @JoinColumn(name = "event_id")
    private List<Event> events;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @PositiveOrZero
    private Long id;
    @Column(name = "pinned", nullable = false)
    @NotNull
    private Boolean pinned;
    @Column(name = "title", nullable = false)
    @NotNull
    @NotBlank
    private String title;
}
