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
    public List<EventFullDto> searchEvents(List<Integer> users, List<String> states, List<Integer> categories,
                                           String rangeStart, String rangeEnd, Integer from, Integer size) {
        return null;
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
        Duration duration = Duration.between(event.getPublishedOn(),event.getEventDate());
        if (duration.toMinutes() >= 60l) {
            if (event.getState().equals(State.PENDING)) {
                event.setState(State.PUBLISHED);
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
        if (event.getState().equals(State.PUBLISHED)) {
            throw new ValidationException("Статус события - PUBLISHED!");
        }
        event.setState(State.CANCELED);
        return EventMapper.toEventFullDto(eventRepository.save(event));
    }

    @Override
    public List<EventShortDto> findEvents(String text, List<Integer> categories, Boolean paid,
                                          String rangeStart, String rangeEnd, Boolean onlyAvailable,
                                          String sort, Integer from, Integer size) {
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
        return null;
    }

    @Override
    public EventFullDto addNewEvent(Long userId, NewEventDto newEvent) {
        return null;
    }

    @Override
    public EventFullDto findEventOfUser(Long userId, Long eventId) {
        return null;
    }

    @Override
    public EventFullDto cancelEventOfUser(Long userId, Long eventId) {
        return null;
    }
}
