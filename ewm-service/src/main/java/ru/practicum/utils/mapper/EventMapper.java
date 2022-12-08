package ru.practicum.utils.mapper;

import ru.practicum.model.Category;
import ru.practicum.model.Event;
import ru.practicum.model.Location;
import ru.practicum.model.User;
import ru.practicum.model.dto.event.EventFullDto;
import ru.practicum.model.dto.event.EventShortDto;
import ru.practicum.model.dto.event.NewEventDto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class EventMapper {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static EventShortDto toEventShortDto(Event event) {
        return EventShortDto.builder()
                .annotation(event.getAnnotation())
                .category(EventShortDto.CategoryDto.builder()
                        .id(event.getCategory().getId())
                        .name(event.getCategory().getName())
                        .build())
                .confirmedRequests(event.getConfirmedRequests())
                .eventDate(event.getEventDate())
                .id(event.getId())
                .initiator(EventShortDto.UserShortDto.builder()
                        .id(event.getInitiator().getId())
                        .name(event.getInitiator().getName())
                        .build())
                .paid(event.getPaid())
                .title(event.getTitle())
                .build();
    }

    public static EventFullDto toEventFullDto(Event event, Long views, Integer comments) {
        String createdOn = event.getCreatedOn().format(formatter);
        String eventDate = event.getEventDate().format(formatter);
        if (event.getPublishedOn() == null) {
            return EventFullDto.builder()
                    .annotation(event.getAnnotation())
                    .category(EventFullDto.CategoryDto.builder()
                            .id(event.getCategory().getId())
                            .name(event.getCategory().getName())
                            .build())
                    .confirmedRequests(event.getConfirmedRequests())
                    .comments(comments)
                    .createdOn(createdOn)
                    .description(event.getDescription())
                    .eventDate(eventDate)
                    .id(event.getId())
                    .initiator(EventFullDto.UserShortDto.builder()
                            .id(event.getInitiator().getId())
                            .name(event.getInitiator().getName())
                            .build())
                    .location(EventFullDto.Location.builder()
                            .lat(event.getLocation().getLat())
                            .lon(event.getLocation().getLon())
                            .build())
                    .paid(event.getPaid())
                    .participantLimit(event.getParticipantLimit())
                    .requestModeration(event.getRequestModeration())
                    .state(event.getState())
                    .title(event.getTitle())
                    .views(views)
                    .build();
        }
        String publishedOn = event.getPublishedOn().format(formatter);
        return EventFullDto.builder()
                .annotation(event.getAnnotation())
                .category(EventFullDto.CategoryDto.builder()
                        .id(event.getCategory().getId())
                        .name(event.getCategory().getName())
                        .build())
                .confirmedRequests(event.getConfirmedRequests())
                .comments(comments)
                .createdOn(createdOn)
                .description(event.getDescription())
                .eventDate(eventDate)
                .id(event.getId())
                .initiator(EventFullDto.UserShortDto.builder()
                        .id(event.getInitiator().getId())
                        .name(event.getInitiator().getName())
                        .build())
                .location(EventFullDto.Location.builder()
                        .lat(event.getLocation().getLat())
                        .lon(event.getLocation().getLon())
                        .build())
                .paid(event.getPaid())
                .participantLimit(event.getParticipantLimit())
                .publishedOn(publishedOn)
                .requestModeration(event.getRequestModeration())
                .state(event.getState())
                .title(event.getTitle())
                .views(views)
                .build();
    }

    public static Event toEvent(User user, Location location, Category category, NewEventDto newEvent) {
        return Event.builder()
                .annotation(newEvent.getAnnotation())
                .category(category)
                .createdOn(LocalDateTime.now().withNano(0))
                .description(newEvent.getDescription())
                .eventDate(LocalDateTime.parse(newEvent.getEventDate(), formatter))
                .initiator(user)
                .location(location)
                .paid(newEvent.getPaid())
                .participantLimit(newEvent.getParticipantLimit())
                .requestModeration(newEvent.getRequestModeration())
                .title(newEvent.getTitle())
                .build();
    }
}
