package ru.practicum.service;

import ru.practicum.model.dto.ParticipationRequestDto;

public interface RequestService {
    ParticipationRequestDto findRequestsOfUser(Long userId, Long eventId);

    ParticipationRequestDto confirmRequest(Long userId, Long eventId, Long reqId);

    ParticipationRequestDto rejectRequest(Long userId, Long eventId, Long reqId);

    ParticipationRequestDto findRequestsOfUserFromOtherEvents(Long userId);

    ParticipationRequestDto addNewRequestOfUser(Long userId, Long eventId);

    ParticipationRequestDto cancelRequest(Long userId, Long requestId);
}
