package ru.practicum.service;

import ru.practicum.model.enumstatus.Sort;
import ru.practicum.model.enumstatus.StateEvent;
import ru.practicum.model.dto.event.AdminUpdateEventRequest;
import ru.practicum.model.dto.event.EventFullDto;
import ru.practicum.model.dto.event.EventShortDto;
import ru.practicum.model.dto.event.NewEventDto;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

public interface EventService {

    List<EventFullDto> searchEvents(List<Long> users, List<StateEvent> stateEvents, List<Long> categories, LocalDateTime rangeStart, LocalDateTime rangeEnd, Integer from, Integer size);

    EventFullDto updateEventRequest(Long eventId, AdminUpdateEventRequest eventRequest);

    EventFullDto publishingEvent(Long eventId);

    EventFullDto rejectionEvent(Long eventId);

    List<EventShortDto> findEvents(String text, List<Long> categories, Boolean paid, LocalDateTime rangeStart, LocalDateTime rangeEnd, Boolean onlyAvailable, Sort sort, Integer from, Integer size, HttpServletRequest request);

    EventFullDto findEventById(Long id, HttpServletRequest request);

    List<EventShortDto> findEventsOfUser(Long userId, Integer from, Integer size);

    EventFullDto updateEvent(Long userId, NewEventDto updateEvent);

    EventFullDto addNewEvent(Long userId, NewEventDto newEvent);

    EventFullDto findEventOfUser(Long userId, Long eventId);

    EventFullDto cancelEventOfUser(Long userId, Long eventId);
}
