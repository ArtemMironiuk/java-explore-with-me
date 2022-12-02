package ru.practicum.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id")
    @PositiveOrZero
    private Long id;
    @NotNull
    @NotBlank
    @Column(name = "title")
    private String title;
    @NotNull
    @NotBlank
    @Column(name = "annotation", nullable = false)
    private String annotation;
    @Column(name = "description")
    private String description;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;
    @Column(name = "confirmed_requests")
    private Integer confirmedRequests;
    @Future
    @NotNull
    @Column(name = "event_date", nullable = false)
    private LocalDateTime eventDate;
    @NotNull
    @Column(name = "paid", nullable = false)
    private Boolean paid;
    @Column(name = "request_moderation")
    private Boolean requestModeration;
    @Column(name = "participant_limit")
    private Integer participantLimit;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "initiator_id", nullable = false)
    private User initiator;
    @NotNull
    @Column(name = "created_on", nullable = false)
    private LocalDateTime createdOn;
    @Column(name = "published_on")
    private LocalDateTime publishedOn;
    @Column(name = "event_state")
    @Enumerated(EnumType.STRING)
    private StateEvent state;
    @NotNull
    @OneToOne
    @JoinColumn(name = "location", nullable = false)
    private Location location;
}
