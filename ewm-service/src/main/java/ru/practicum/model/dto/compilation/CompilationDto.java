package ru.practicum.model.dto.compilation;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class CompilationDto {
    private Long id;
    private Boolean pinned;
    private String title;
    private Set<EventShortDto> events;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EventShortDto {
        private String annotation;
        private CategoryDto category;
        private Integer confirmedRequests;
        private LocalDateTime eventDate;
        private Long id;
        private UserShortDto initiator;
        private Boolean paid;
        private String title;
        private Integer views;

        @Data
        @Builder
        @NoArgsConstructor
        @AllArgsConstructor
        public static class CategoryDto {
            private Long id;
            private String name;
        }

        @Data
        @Builder
        @NoArgsConstructor
        @AllArgsConstructor
        public static class UserShortDto {
            private Long id;
            private String name;
        }
    }
}
