package ru.practicum.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.handler.exception.ObjectNotFoundException;
import ru.practicum.handler.exception.ValidationException;
import ru.practicum.model.*;
import ru.practicum.model.dto.event.*;
import ru.practicum.repository.CategoryRepository;
import ru.practicum.repository.EventRepository;
import ru.practicum.repository.LocationRepository;
import ru.practicum.repository.UserRepository;
import ru.practicum.service.EventService;
import ru.practicum.utils.mapper.EventMapper;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


    @Override
    public List<EventFullDto> searchEvents(Long[] users, StateEvent[] stateEvents, Long[] categories, String rangeStart,
                                           String rangeEnd, Integer from, Integer size) {
        Pageable pageable = PageRequest.of(from / size, size);
        LocalDateTime start = LocalDateTime.parse(rangeStart, formatter);
        LocalDateTime end = LocalDateTime.parse(rangeEnd, formatter);
        return eventRepository.searchEvent(users, stateEvents, categories, start, end, pageable)
                .stream()
                .map(EventMapper::toEventFullDto)
                .collect(toList());
    }

    @Override
    public EventFullDto updateEventRequest(Long eventId, AdminUpdateEventRequest eventRequest) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ObjectNotFoundException("Нет такого события!"));
        if (eventRequest.getAnnotation() != null && !eventRequest.getAnnotation().isEmpty()) {
            event.setAnnotation(eventRequest.getAnnotation());
        }
        if (eventRequest.getCategory() != null && eventRequest.getCategory() > 0) {
            Category category = categoryRepository.findById(eventRequest.getCategory())
                    .orElseThrow(() -> new ObjectNotFoundException("Нет такой категории!"));
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
        return EventMapper.toEventFullDto(eventRepository.save(event));
    }

    @Override
    public EventFullDto publishingEvent(Long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ObjectNotFoundException("Нет такого события!"));
        Duration duration = Duration.between(LocalDateTime.now(), event.getEventDate());
        if (duration.toMinutes() >= 60) {
            if (event.getStateEvent().equals(StateEvent.PENDING)) {
                event.setStateEvent(StateEvent.PUBLISHED);
                event.setPublishedOn(LocalDateTime.now().withNano(0));
                return EventMapper.toEventFullDto(eventRepository.save(event));
            }
            throw new ValidationException("У события нет состояния ожидания публикации!");
        }
        throw new ValidationException("Дата начала события должна быть не ранее чем за час от даты публикации");
    }

    @Override
    public EventFullDto rejectionEvent(Long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ObjectNotFoundException("Нет такого события!"));
        if (event.getStateEvent().equals(StateEvent.PUBLISHED)) {
            throw new ValidationException("Статус события - PUBLISHED!");
        }
        event.setStateEvent(StateEvent.CANCELED);
        return EventMapper.toEventFullDto(eventRepository.save(event));
    }

    @Override
    public List<EventShortDto> findEvents(String text, List<Integer> categories, Boolean paid,
                                          String rangeStart, String rangeEnd, Boolean onlyAvailable,
                                          String sort, Integer from, Integer size) {
        Pageable pageable = PageRequest.of(from / size, size);
        //TODO сертвис статистики
        return null;
    }

    @Override
    public EventFullDto findEventById(Long id) {
        //TODO сертвис статистики
        return null;
    }

    @Override
    public List<EventShortDto> findEventsOfUser(Long userId, Integer from, Integer size) {
        Pageable pageable = PageRequest.of(from / size, size);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ObjectNotFoundException("Нет такого пользователя!"));
        return eventRepository.findByInitiator(userId, pageable)
                .stream()
                .map(EventMapper::toEventShortDto)
                .collect(toList());
    }

    @Override
    public EventFullDto updateEvent(Long userId, UpdateEventRequestDto updateEvent) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ObjectNotFoundException("Нет такого пользователя!"));
        Event event = eventRepository.findById(updateEvent.getEventId())
                .orElseThrow(() -> new ObjectNotFoundException("Нет такого события!"));
        Duration duration = Duration.between(LocalDateTime.now().withNano(0), event.getEventDate());
        if (duration.toMinutes() >= 120) {
            if (event.getStateEvent().equals(StateEvent.CANCELED) || event.getRequestModeration() == true) {
                if (event.getStateEvent().equals(StateEvent.CANCELED)) {
                    event.setRequestModeration(true);
                }
                if (updateEvent.getAnnotation() != null && !updateEvent.getAnnotation().isEmpty()) {
                    event.setAnnotation(updateEvent.getAnnotation());
                }
                if (updateEvent.getCategory() != null && updateEvent.getCategory() > 0) {
                    Category category = categoryRepository.findById(Long.valueOf(updateEvent.getCategory()))
                            .orElseThrow(() -> new ObjectNotFoundException("Нет такой категории!"));
                    event.setCategory(category);
                }
                if (updateEvent.getDescription() != null && !updateEvent.getDescription().isEmpty()) {
                    event.setDescription(updateEvent.getDescription());
                }
                if (updateEvent.getEventDate() != null) {
                    event.setEventDate(updateEvent.getEventDate());
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
                return EventMapper.toEventFullDto(eventRepository.save(event));
            }
            throw new ValidationException("Событие либо опубликовано, либо не находится в состоянии ожидания модерации");
        }
        throw new ValidationException("Событие начнется менее чем через 2 часа");
    }

    @Override
    public EventFullDto addNewEvent(Long userId, NewEventDto newEvent) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ObjectNotFoundException("Нет такого пользователя!"));
        Category category = categoryRepository.findById(Long.valueOf(newEvent.getCategory()))
                .orElseThrow(() -> new ObjectNotFoundException("Нет такой категории!"));
        Location location = locationRepository.save(newEvent.getLocation());
        Event event = EventMapper.toEvent(user, location, category, newEvent);
        return EventMapper.toEventFullDto(eventRepository.save(event));
    }

    @Override
    public EventFullDto findEventOfUser(Long userId, Long eventId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ObjectNotFoundException("Нет такого пользователя!"));
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ObjectNotFoundException("Нет такого события!"));
        if (event.getInitiator().getId() != userId) {
            throw new ValidationException("Пользователь не добавлял это событие!");
        }
        return EventMapper.toEventFullDto(event);
    }

    @Override
    public EventFullDto cancelEventOfUser(Long userId, Long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ObjectNotFoundException("Нет такого события!"));
        if (event.getInitiator().getId() != userId) {
            throw new ValidationException("Пользователь не добавлял это событие!");
        }
        if (event.getRequestModeration() != true) {
            throw new ValidationException("Событие уже отмодерировано!");
        }
        event.setStateEvent(StateEvent.CANCELED);
        return EventMapper.toEventFullDto(eventRepository.save(event));
    }
}
