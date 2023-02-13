package ru.practicum.model.dto;

import lombok.*;

import javax.validation.constraints.Positive;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class RatingDtoView {
    @Positive
    private Integer like;
    @Positive
    private Integer dislike;
    @Positive
    private Double rating;
}
