package ru.practicum.model;

import lombok.*;
import ru.practicum.model.enumstatus.StateEvent;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id")
    private Long id;
    @Column(name = "title", nullable = false, length = 120)
    private String title;
    @Column(name = "annotation", nullable = false, length = 2000)
    private String annotation;
    @Column(name = "description", length = 7000)
    private String description;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;
    @Column(name = "confirmed_requests")
    private Integer confirmedRequests;
    @Column(name = "event_date", nullable = false)
    private LocalDateTime eventDate;
    @Column(name = "paid", nullable = false)
    private Boolean paid;
    @Column(name = "request_moderation")
    private Boolean requestModeration;
    @Column(name = "participant_limit")
    private Integer participantLimit;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "initiator_id", nullable = false)
    private User initiator;
    @Column(name = "created_on", nullable = false)
    private LocalDateTime createdOn;
    @Column(name = "published_on")
    private LocalDateTime publishedOn;
    @Column(name = "event_state", length = 100)
    @Enumerated(EnumType.STRING)
    private StateEvent state;
    @OneToOne
    @JoinColumn(name = "location", nullable = false)
    private Location location;
}
