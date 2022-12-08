package ru.practicum.model;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "compilations")
public class Compilation {

    @ManyToMany()
    @JoinTable(name = "compilation_events",
            joinColumns = @JoinColumn(name = "compilation_id"),
            inverseJoinColumns = @JoinColumn(name = "event_id"))
    private Set<Event> events;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "pinned", nullable = false)
    private Boolean pinned;
    @Column(name = "title", nullable = false, length = 150)
    private String title;
}
