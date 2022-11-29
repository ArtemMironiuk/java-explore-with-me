package ru.practicum.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.model.dto.ParticipationRequestDto;
import ru.practicum.repository.RequestRepository;
import ru.practicum.service.RequestService;
import ru.practicum.utils.mapper.RequestMapper;

import static java.util.stream.Collectors.toList;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RequestServiceImpl implements RequestService {

    private final RequestRepository requestRepository;

    @Override
    public ParticipationRequestDto findRequestsOfUser(Long userId, Long eventId) {
        return (ParticipationRequestDto) requestRepository.findRequestsOfEvent(userId,eventId)
                .stream()
                .map(RequestMapper::toParticipationRequestDto)
                .collect(toList());
    }

    @Override
    public ParticipationRequestDto confirmRequest(Long userId, Long eventId, Long reqId) {
        return null;
    }

    @Override
    public ParticipationRequestDto rejectRequest(Long userId, Long eventId, Long reqId) {
        return null;
    }

    @Override
    public ParticipationRequestDto findRequestsOfUserFromOtherEvents(Long userId) {
        return null;
    }

    @Override
    public ParticipationRequestDto addNewRequestOfUser(Long userId, Long eventId) {

        return null;
    }

    @Override
    public ParticipationRequestDto cancelRequest(Long userId, Long requestId) {
        return null;
    }
}
