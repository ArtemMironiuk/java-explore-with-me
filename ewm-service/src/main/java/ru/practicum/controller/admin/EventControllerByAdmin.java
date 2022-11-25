package ru.practicum.controller.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
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
    public List<EventFullDto> searchEvents(@RequestParam(name = "users") List<Integer> users,
                                           @RequestParam(name = "states") List<String> states,
                                           @RequestParam(name = "categories") List<Integer> categories,
                                           @RequestParam(name = "rangeStart") String rangeStart,
                                           @RequestParam(name = "rangeEnd") String rangeEnd,
                                           @RequestParam(name = "from", defaultValue = "0") Integer from,
                                           @RequestParam(name = "size", defaultValue = "10") Integer size) {
        log.info("Получен запрос к эндпоинту GET, /admin/events");
        return eventService.searchEvents(users, states, categories, rangeStart, rangeEnd, from, size);
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
