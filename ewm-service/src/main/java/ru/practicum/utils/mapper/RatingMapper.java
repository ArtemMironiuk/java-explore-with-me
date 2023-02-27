package ru.practicum.utils.mapper;

import ru.practicum.model.Event;
import ru.practicum.model.Rating;
import ru.practicum.model.User;
import ru.practicum.model.dto.RatingDto;

public class RatingMapper {
    public static Rating toRating(User user, Event event) {
        return Rating.builder()
                .user(user)
                .event(event)
                .build();
    }

    public static RatingDto toRatingDto(Rating rating) {
        return RatingDto.builder()
                .id(rating.getId())
                .user(RatingDto.UserDto.builder()
                        .id(rating.getUser().getId())
                        .name(rating.getUser().getName())
                        .build())
                .event(RatingDto.EventDto.builder()
                        .id(rating.getEvent().getId())
                        .title(rating.getEvent().getTitle())
                        .build())
                .like(rating.getLike())
                .dislike((rating.getDislike()))
                .build();
    }
}
