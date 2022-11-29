package ru.practicum.controller.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.model.dto.ParticipationRequestDto;
import ru.practicum.service.RequestService;

import javax.validation.constraints.PositiveOrZero;

@RestController
@RequestMapping(path = "/users")
@Slf4j
@RequiredArgsConstructor
@Validated
public class RequestControllerPrivate {

    private final RequestService requestService;

    @GetMapping("/{userId}/events/{eventId}/requests")
    public ParticipationRequestDto findRequestsOfUser(@PathVariable @PositiveOrZero Long userId,
                                                      @PathVariable @PositiveOrZero Long eventId) {
        log.info("Получен запрос к эндпоинту GET, /users/{userId}/requests");
        return requestService.findRequestsOfUser(userId,eventId);
    }

    @PatchMapping("/{userId}/events/{eventId}/requests/{reqId}/confirm")
    public ParticipationRequestDto confirmRequest(@PathVariable @PositiveOrZero Long userId,
                                                  @PathVariable @PositiveOrZero Long eventId,
                                                  @PathVariable @PositiveOrZero Long reqId) {
        log.info("Получен запрос к эндпоинту PATCH, /users/{userId}/events/{eventId}/requests/{reqId}/confirm");
        return requestService.confirmRequest(userId, eventId, reqId);
    }

    @PatchMapping("/{userId}/events/{eventId}/requests/{reqId}/reject")
    public ParticipationRequestDto rejectRequest(@PathVariable @PositiveOrZero Long userId,
                                                 @PathVariable @PositiveOrZero Long eventId,
                                                 @PathVariable @PositiveOrZero Long reqId) {
        log.info("Получен запрос к эндпоинту PATCH, /users/{userId}/events/{eventId}/requests/{reqId}/reject");
        return requestService.rejectRequest(userId, eventId, reqId);
    }

    @GetMapping("/{userId}/requests")
    public ParticipationRequestDto findRequestsOfUserFromOtherEvents(@PathVariable @PositiveOrZero Long userId) {
        log.info("Получен запрос к эндпоинту GET, /users/{userId}/requests");
        return requestService.findRequestsOfUserFromOtherEvents(userId);
    }

    @PostMapping("/{userId}/requests")
    public ParticipationRequestDto addNewRequestOfUser(@PathVariable @PositiveOrZero Long userId,
                                                       @RequestParam @PositiveOrZero Long eventId) {
        log.info("Получен запрос к эндпоинту POST, /users/{userId}/requests");
        return requestService.addNewRequestOfUser(userId, eventId);
    }

    @PatchMapping("/{userId}/requests/{requestId}/cancel")
    public ParticipationRequestDto cancelRequest(@PathVariable @PositiveOrZero Long userId,
                                                 @PathVariable @PositiveOrZero Long requestId) {
        log.info("Получен запрос к эндпоинту PATCH, /users/{userId}/requests/{requestId}/cancel");
        return requestService.cancelRequest(userId, requestId);
    }
}
