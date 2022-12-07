package ru.practicum.model;

import lombok.*;
import ru.practicum.model.enumstatus.StateRequest;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "requests")
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "requester", nullable = false)
    private User requester;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "event", nullable = false)
    private Event event;
    @Column(name = "created", nullable = false)
    private LocalDateTime created;
    @Column(name = "status", length = 150)
    @Enumerated(EnumType.STRING)
    private StateRequest status;
}
