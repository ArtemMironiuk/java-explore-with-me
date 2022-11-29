package ru.practicum.utils.mapper;

import ru.practicum.model.*;
import ru.practicum.model.dto.event.EventFullDto;
import ru.practicum.model.dto.event.EventShortDto;
import ru.practicum.model.dto.event.NewEventDto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class EventMapper {
    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public static EventShortDto toEventShortDto(Event event) {
        return EventShortDto.builder()
                .annotation(event.getAnnotation())
                .category(CategoryMapper.toCategoryDto(event.getCategory()))
                .confirmedRequests(event.getConfirmedRequests())
                .eventDate(event.getEventDate())
                .id(event.getId())
                .initiator(UserMapper.toUserShortDto(event.getInitiator()))
                .paid(event.getPaid())
                .title(event.getTitle())
                .build();
    }

    public static EventFullDto toEventFullDto(Event event) {
        String createdOn = event.getCreatedOn().format(formatter);
        String eventDate = event.getEventDate().format(formatter);
        String publishedOn = event.getPublishedOn().format(formatter);
        return EventFullDto.builder()
                .annotation(event.getAnnotation())
                .category(CategoryMapper.toCategoryDto(event.getCategory()))
                .confirmedRequest(event.getConfirmedRequests())
                .createdOn(createdOn)
                .description(event.getDescription())
                .eventDate(eventDate)
                .id(event.getId())
                .initiator(UserMapper.toUserShortDto(event.getInitiator()))
                .location(event.getLocation())
                .paid(event.getPaid())
                .participantLimit(event.getParticipantLimit())
                .publishedOn(publishedOn)
                .requestModeration(event.getRequestModeration())
                .state(event.getState())
                .title(event.getTitle())
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
                .state(State.PENDING)
                .title(newEvent.getTitle())
                .build();
    }
}
