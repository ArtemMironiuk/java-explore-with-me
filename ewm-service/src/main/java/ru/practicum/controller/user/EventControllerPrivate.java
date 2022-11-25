package ru.practicum.controller.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.model.dto.event.EventFullDto;
import ru.practicum.model.dto.event.EventShortDto;
import ru.practicum.model.dto.event.NewEventDto;
import ru.practicum.model.dto.event.UpdateEventRequestDto;
import ru.practicum.service.EventService;
import ru.practicum.service.UserService;

import javax.validation.Valid;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequestMapping(path = "/users")
@Slf4j
@RequiredArgsConstructor
@Validated
public class EventControllerPrivate {

    private final EventService eventService;

    @GetMapping("/{userId}/events")
    public List<EventShortDto> findEventsOfUser(@PathVariable @PositiveOrZero Long userId,
                                                @RequestParam(name = "from", defaultValue = "0") Integer from,
                                                @RequestParam(name = "size", defaultValue = "10") Integer size) {
        log.info("Получен запрос к эндпоинту GET, /users/{userId}/events");
        return eventService.findEventsOfUser(userId, from, size);
    }

    @PatchMapping("/{userId}/events")
    public EventFullDto updateEvent(@PathVariable @PositiveOrZero Long userId,
                                    @RequestBody @Valid UpdateEventRequestDto updateEvent) {
        log.info("Получен запрос к эндпоинту PATCH, /users/{userId}/events");
        return eventService.updateEvent(userId, updateEvent);
    }

    @PostMapping("/{userId}/events")
    public EventFullDto addNewEvent(@PathVariable @PositiveOrZero Long userId,
                                    @RequestBody @Valid NewEventDto newEvent) {
        log.info("Получен запрос к эндпоинту POST, /users/{userId}/events");
        return eventService.addNewEvent(userId, newEvent);
    }

    @GetMapping("/{userId}/events/{eventId}")
    public EventFullDto findEventOfUser(@PathVariable @PositiveOrZero Long userId,
                                        @PathVariable @PositiveOrZero Long eventId) {
        log.info("Получен запрос к эндпоинту GET, /users/{userId}/events/{eventId}");
        return eventService.findEventOfUser(userId, eventId);
    }

    @PatchMapping("/{userId}/events/{eventId}")
    public EventFullDto cancelEventOfUser(@PathVariable @PositiveOrZero Long userId,
                                          @PathVariable @PositiveOrZero Long eventId) {
        log.info("Получен запрос к эндпоинту PATCH, /users/{userId}/events/{eventId}");
        return eventService.cancelEventOfUser(userId, eventId);
    }
}
