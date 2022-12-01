package ru.practicum.service;

import ru.practicum.model.StateEvent;
import ru.practicum.model.dto.event.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface EventService {

    List<EventFullDto> searchEvents(Long[] users, StateEvent[] stateEvents, Long[] categories, String rangeStart, String rangeEnd, Integer from, Integer size);

    EventFullDto updateEventRequest(Long eventId, AdminUpdateEventRequest eventRequest);

    EventFullDto publishingEvent(Long eventId);

    EventFullDto rejectionEvent(Long eventId);

    List<EventShortDto> findEvents(String text, List<Integer> categories, Boolean paid, String rangeStart, String rangeEnd, Boolean onlyAvailable, String sort, Integer from, Integer size, HttpServletRequest request);

    EventFullDto findEventById(Long id, HttpServletRequest request);

    List<EventShortDto> findEventsOfUser(Long userId, Integer from, Integer size);

    EventFullDto updateEvent(Long userId, UpdateEventRequestDto updateEvent);

    EventFullDto addNewEvent(Long userId, NewEventDto newEvent);

    EventFullDto findEventOfUser(Long userId, Long eventId);

    EventFullDto cancelEventOfUser(Long userId, Long eventId);
}
