package ru.practicum.model.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class RatingDto {
    private Long id;
    private UserDto user;
    private EventDto event;
    private Long like;
    private Long dislike;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserDto {
        private Long id;
        private String name;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EventDto {
        private Long id;
        private String title;
    }
}
