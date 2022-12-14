package ru.practicum.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.client.StatsClient;
import ru.practicum.handler.exception.ObjectNotFoundException;
import ru.practicum.handler.exception.ValidationException;
import ru.practicum.model.*;
import ru.practicum.model.dto.event.*;
import ru.practicum.model.enumstatus.Sort;
import ru.practicum.model.enumstatus.StateEvent;
import ru.practicum.model.enumstatus.StateRequest;
import ru.practicum.repository.*;
import ru.practicum.service.EventService;
import ru.practicum.utils.mapper.EventMapper;
import ru.practicum.utils.mapper.LocationMapper;

import javax.servlet.http.HttpServletRequest;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final LocationRepository locationRepository;
    private final RequestRepository requestRepository;
    private final StatsClient statsClient;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public List<EventFullDto> searchEvents(List<Long> users, List<StateEvent> stateEvents, List<Long> categories, LocalDateTime rangeStart,
                                           LocalDateTime rangeEnd, Integer from, Integer size) {
        Pageable pageable = PageRequest.of(from / size, size);
        return eventRepository.searchEvent(users, stateEvents, categories, rangeStart, rangeEnd, pageable)
                .stream()
                .map(event -> EventMapper.toEventFullDto(event, statsClient.getViewsStat(event)))
                .collect(toList());
    }

    @Transactional
    @Override
    public EventFullDto updateEventRequest(Long eventId, AdminUpdateEventRequest eventRequest) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ObjectNotFoundException("?????? ???????????? ??????????????!"));
        if (eventRequest.getAnnotation() != null && !eventRequest.getAnnotation().isEmpty()) {
            event.setAnnotation(eventRequest.getAnnotation());
        }
        if (eventRequest.getCategory() != null && eventRequest.getCategory() > 0) {
            Category category = categoryRepository.findById(eventRequest.getCategory())
                    .orElseThrow(() -> new ObjectNotFoundException("?????? ?????????? ??????????????????!"));
            event.setCategory(category);
        }
        if (eventRequest.getDescription() != null && !eventRequest.getDescription().isEmpty()) {
            event.setDescription(eventRequest.getDescription());
        }
        if (eventRequest.getEventDate() != null && !eventRequest.getEventDate().isEmpty()) {
            LocalDateTime eventDate = LocalDateTime.parse(eventRequest.getEventDate(), formatter);
            event.setEventDate(eventDate);
        }
        if (eventRequest.getLocation() != null) {
            Location location = locationRepository.findByLatAndLon(eventRequest.getLocation().getLat(),
                    eventRequest.getLocation().getLon());
            if (location != null) {
                event.setLocation(location);
            }
            event.setLocation(locationRepository.save(eventRequest.getLocation()));
        }
        if (eventRequest.getPaid() != null) {
            event.setPaid(eventRequest.getPaid());
        }
        if (eventRequest.getParticipantLimit() != null && eventRequest.getParticipantLimit() >= 0) {
            event.setParticipantLimit(eventRequest.getParticipantLimit());
        }
        if (eventRequest.getRequestModeration() != null) {
            event.setRequestModeration(eventRequest.getRequestModeration());
        }
        if (eventRequest.getTitle() != null && !eventRequest.getTitle().isEmpty()) {
            event.setTitle(eventRequest.getTitle());
        }
        return EventMapper.toEventFullDto(eventRepository.save(event), statsClient.getViewsStat(event));
    }

    @Override
    @Transactional
    public EventFullDto publishingEvent(Long eventId) {
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new ObjectNotFoundException("?????? ???????????? ??????????????!"));
        if (event.getState().equals(StateEvent.CANCELED) || event.getState().equals(StateEvent.PUBLISHED))
            throw new ValidationException("! ?????????????? ?????? ???????????????? ?????? ????????????????????????!");
        LocalDateTime dateAndTimeNowPublish = LocalDateTime.now();
        Duration duration = Duration.between(dateAndTimeNowPublish, event.getEventDate());
        if (duration.toMinutes() < 60)
            throw new ValidationException("! ???????? ???????????? ?????????????? ???????????? ???????? ???? ?????????? ?????? ???? ?????? ???? ???????? ????????????????????");
        event.setPublishedOn(dateAndTimeNowPublish);
        event.setState(StateEvent.PUBLISHED);
        return EventMapper.toEventFullDto(eventRepository.save(event), statsClient.getViewsStat(event));
    }

    @Override
    public EventFullDto rejectionEvent(Long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ObjectNotFoundException("?????? ???????????? ??????????????!"));
        if (event.getState().equals(StateEvent.PUBLISHED)) {
            throw new ValidationException("???????????? ?????????????? - PUBLISHED!");
        }
        event.setState(StateEvent.CANCELED);
        return EventMapper.toEventFullDto(eventRepository.save(event), statsClient.getViewsStat(event));
    }

    @Override
    public List<EventShortDto> findEvents(String text, List<Long> categories, Boolean paid,
                                          LocalDateTime rangeStart, LocalDateTime rangeEnd, Boolean onlyAvailable,
                                          Sort sort, Integer from, Integer size, HttpServletRequest request) {
        Pageable pageable = PageRequest.of(from / size, size);
        List<EventShortDto> events = eventRepository.findEvents(text, categories, paid, rangeStart, rangeEnd, onlyAvailable, pageable)
                .stream()
                .map(EventMapper::toEventShortDto)
                .collect(toList());
        if (sort != null) {
            switch (sort) {
                case EVENT_DATE:
                    events = events.stream()
                            .sorted(Comparator.comparing(EventShortDto::getEventDate))
                            .collect(toList());
                    break;
                case VIEWS:
                    events = events.stream()
                            .sorted(Comparator.comparing(EventShortDto::getViews))
                            .collect(toList());
                    break;
            }
        }
        statsClient.save(request);
        return events;
    }

    @Override
    public EventFullDto findEventById(Long id, HttpServletRequest request) {
        Event event = eventRepository.findByIdAndState(id, StateEvent.PUBLISHED)
                .orElseThrow(() -> new ObjectNotFoundException("?????? ???????????? ??????????????!"));
        event.setConfirmedRequests(Integer.valueOf(String.valueOf(requestRepository.countByEvent_IdAndStatus(id, StateRequest.CONFIRMED))));
        statsClient.save(request);
        return EventMapper.toEventFullDto(event, statsClient.getViewsStat(event));
    }

    @Override
    public List<EventShortDto> findEventsOfUser(Long userId, Integer from, Integer size) {
        Pageable pageable = PageRequest.of(from / size, size);
        userRepository.findById(userId)
                .orElseThrow(() -> new ObjectNotFoundException("?????? ???????????? ????????????????????????!"));
        return eventRepository.findByInitiatorId(userId, pageable)
                .stream()
                .map(EventMapper::toEventShortDto)
                .collect(toList());
    }

    @Transactional
    @Override
    public EventFullDto updateEvent(Long userId, UpdateEventRequest updateEvent) {
        userRepository.findById(userId)
                .orElseThrow(() -> new ObjectNotFoundException("?????? ???????????? ????????????????????????!"));
        Event event = eventRepository.findById(updateEvent.getEventId())
                .orElseThrow(() -> new ObjectNotFoundException("?????? ???????????? ??????????????!"));
        Duration duration = Duration.between(LocalDateTime.now().withNano(0), event.getEventDate());
        if (duration.toMinutes() >= 120) {
            if (event.getState().equals(StateEvent.CANCELED) || event.getState().equals(StateEvent.PENDING)) {
                if (event.getState().equals(StateEvent.CANCELED)) {
                    event.setState(StateEvent.PENDING);
                }
                if (updateEvent.getAnnotation() != null && !updateEvent.getAnnotation().isEmpty()) {
                    event.setAnnotation(updateEvent.getAnnotation());
                }
                if (updateEvent.getCategory() != null && updateEvent.getCategory() > 0) {
                    Category category = categoryRepository.findById(Long.valueOf(updateEvent.getCategory()))
                            .orElseThrow(() -> new ObjectNotFoundException("?????? ?????????? ??????????????????!"));
                    event.setCategory(category);
                }
                if (updateEvent.getDescription() != null && !updateEvent.getDescription().isEmpty()) {
                    event.setDescription(updateEvent.getDescription());
                }
                if (updateEvent.getEventDate() != null) {
                    event.setEventDate(LocalDateTime.parse(updateEvent.getEventDate(), formatter));
                }
                if (updateEvent.getPaid() != null) {
                    event.setPaid(updateEvent.getPaid());
                }
                if (updateEvent.getParticipantLimit() != null) {
                    event.setParticipantLimit(updateEvent.getParticipantLimit());
                }
                if (updateEvent.getTitle() != null) {
                    event.setTitle(updateEvent.getTitle());
                }
                return EventMapper.toEventFullDto(eventRepository.save(event), statsClient.getViewsStat(event));
            }
            throw new ValidationException("?????????????? ???????? ????????????????????????, ???????? ???? ?????????????????? ?? ?????????????????? ???????????????? ??????????????????");
        }
        throw new ValidationException("?????????????? ???????????????? ?????????? ?????? ?????????? 2 ????????");
    }

    @Transactional
    @Override
    public EventFullDto addNewEvent(Long userId, NewEventDto newEvent) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ObjectNotFoundException("?????? ???????????? ????????????????????????!"));
        Category category = categoryRepository.findById(Long.valueOf(newEvent.getCategory()))
                .orElseThrow(() -> new ObjectNotFoundException("?????? ?????????? ??????????????????!"));
        Location location = locationRepository.save(LocationMapper.toLocation(newEvent.getLocation()));
        Event event = EventMapper.toEvent(user, location, category, newEvent);
        event.setConfirmedRequests(0);
        event.setState(StateEvent.PENDING);
        Event eventNew = eventRepository.save(event);
        return EventMapper.toEventFullDto(eventNew, statsClient.getViewsStat(eventNew));
    }

    @Override
    public EventFullDto findEventOfUser(Long userId, Long eventId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new ObjectNotFoundException("?????? ???????????? ????????????????????????!"));
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ObjectNotFoundException("?????? ???????????? ??????????????!"));
        if (!event.getInitiator().getId().equals(userId)) {
            throw new ValidationException("???????????????????????? ???? ???????????????? ?????? ??????????????!");
        }
        return EventMapper.toEventFullDto(event, statsClient.getViewsStat(event));
    }

    @Override
    public EventFullDto cancelEventOfUser(Long userId, Long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ObjectNotFoundException("?????? ???????????? ??????????????!"));
        if (!event.getInitiator().getId().equals(userId)) {
            throw new ValidationException("???????????????????????? ???? ???????????????? ?????? ??????????????!");
        }
        if (!event.getRequestModeration() && !event.getState().equals(StateEvent.PENDING)) {
            throw new ValidationException("?????????????? ?????? ????????????????????????????!");
        }
        event.setState(StateEvent.CANCELED);
        return EventMapper.toEventFullDto(eventRepository.save(event), statsClient.getViewsStat(event));
    }
}
