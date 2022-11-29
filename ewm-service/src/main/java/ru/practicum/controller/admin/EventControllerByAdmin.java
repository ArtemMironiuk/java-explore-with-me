package ru.practicum.controller.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.handler.exception.ValidationException;
import ru.practicum.model.State;
import ru.practicum.model.dto.event.AdminUpdateEventRequest;
import ru.practicum.model.dto.event.EventFullDto;
import ru.practicum.service.EventService;

import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequestMapping(path = "/admin/events")
@Slf4j
@RequiredArgsConstructor
@Validated
public class EventControllerByAdmin {

    private final EventService eventService;

    @GetMapping
    public List<EventFullDto> searchEvents(@RequestParam(name = "users") Long[] users,
                                           @RequestParam(name = "states") String[] states,
                                           @RequestParam(name = "categories") Long[] categories,
                                           @RequestParam(name = "rangeStart") String rangeStart,
                                           @RequestParam(name = "rangeEnd") String rangeEnd,
                                           @RequestParam(name = "from", defaultValue = "0") Integer from,
                                           @RequestParam(name = "size", defaultValue = "10") Integer size) {
        log.info("Получен запрос к эндпоинту GET, /admin/events");
        try {
            State[] stateNew = new State[3];
            for (int i = 0; i < states.length; i++) {
                State state = State.valueOf(states[i]);
                stateNew[i] = state;
            }
            return eventService.searchEvents(users, stateNew, categories, rangeStart, rangeEnd, from, size);
        } catch (IllegalArgumentException e) {
            throw new ValidationException("Unknown state: UNSUPPORTED_STATUS");
        }
    }

    @PutMapping("/{eventId}")
    public EventFullDto updateEventRequest(@PathVariable @PositiveOrZero Long eventId,
                                           @RequestBody AdminUpdateEventRequest eventRequest) {
        log.info("Получен запрос к эндпоинту PUT, /admin/events/{eventId}");
        return eventService.updateEventRequest(eventId, eventRequest);
    }

    @PatchMapping("/{eventId}/publish")
    public EventFullDto publishingEvent(@PathVariable @PositiveOrZero Long eventId) {
        return eventService.publishingEvent(eventId);
    }

    @PatchMapping("/{eventId}/reject")
    public EventFullDto rejectionEvent(@PathVariable @PositiveOrZero Long eventId) {
        return eventService.rejectionEvent(eventId);
    }
}
