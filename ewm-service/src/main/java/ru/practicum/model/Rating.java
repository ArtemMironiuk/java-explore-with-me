package ru.practicum.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "rating")
public class Rating {
    private Long id;
    @OneToOne
    private User user;
    @OneToOne
    private Event event;
    private Long like;
    private Long dislike;
}
