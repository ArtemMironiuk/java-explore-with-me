package ru.practicum.controller.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.model.dto.event.EventFullDto;
import ru.practicum.model.dto.event.EventShortDto;
import ru.practicum.service.EventService;
import ru.practicum.service.UserService;

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
    public List<EventShortDto> findEvents(@PathVariable @PositiveOrZero Long userId,
                                          @RequestParam(name = "from", defaultValue = "0") Integer from,
                                          @RequestParam(name = "size", defaultValue = "10") Integer size) {
        log.info("Получен запрос к эндпоинту GET, /users/{userId}/events");
        return eventService.findEvents(userId);
    }

    @PatchMapping("/{userId}/events")
    public EventFullDto updateUser(@PathVariable @PositiveOrZero Long userId,
                                   @RequestBody UpdateEventRequest updateEvent) {
        log.info("Получен запрос к эндпоинту PATCH, /users/{userId}/events");
        return eventService.updateUser(userId, updateEvent);
    }

    @PostMapping
    public UserDto createUser(@RequestBody UserDto userDto) {
        log.info("Получен запрос к эндпоинту POST, /users");
        return userService.createUser(userDto);
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable Long userId) {
        log.info("Получен запрос к эндпоинту DELETE, /users/{userId}");
        userService.deleteUser(userId);
    }
}
