package ru.practicum.controller.notuser;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.model.dto.event.EventFullDto;
import ru.practicum.model.dto.event.EventShortDto;
import ru.practicum.service.EventService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequestMapping(path = "/events")
@Slf4j
@RequiredArgsConstructor
@Validated
public class EventControllerPublic {

    private final EventService eventService;

    @GetMapping
    public List<EventShortDto> findEvents(@RequestParam(name = "text") String text,
                                          @RequestParam(name ="categories") List<Integer> categories,
                                          @RequestParam(name ="paid") Boolean paid,
                                          @RequestParam(name ="rangeStart") String rangeStart,
                                          @RequestParam(name ="rangeEnd") String rangeEnd,
                                          @RequestParam(name ="onlyAvailable", defaultValue = "false") Boolean onlyAvailable,
                                          @RequestParam(name = "sort") String sort,
                                          @RequestParam(name = "from", defaultValue = "0") Integer from,
                                          @RequestParam(name = "size", defaultValue = "10") Integer size,
                                          HttpServletRequest request) {
        log.info("Получен запрос к эндпоинту GET, /events");
        return eventService.findEvents(text, categories, paid, rangeStart, rangeEnd, onlyAvailable, sort, from, size, request);
    }

    @GetMapping("/{id}")
    public EventFullDto findEventById(@PathVariable @PositiveOrZero Long id, HttpServletRequest request) {
        log.info("Получен запрос к эндпоинту GET, /events/id");
        return eventService.findEventById(id, request);
    }
}