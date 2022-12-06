package ru.practicum.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.handler.exception.ObjectNotFoundException;
import ru.practicum.handler.exception.ValidationException;
import ru.practicum.model.*;
import ru.practicum.model.dto.ParticipationRequestDto;
import ru.practicum.model.enumstatus.StateEvent;
import ru.practicum.model.enumstatus.StateRequest;
import ru.practicum.repository.EventRepository;
import ru.practicum.repository.RequestRepository;
import ru.practicum.repository.UserRepository;
import ru.practicum.service.RequestService;
import ru.practicum.utils.mapper.RequestMapper;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RequestServiceImpl implements RequestService {

    private final RequestRepository requestRepository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    @Override
    public List<ParticipationRequestDto> findRequestsOfUser(Long userId, Long eventId) {
        return requestRepository.findRequestsOfEvent(userId, eventId)
                .stream()
                .map(RequestMapper::toParticipationRequestDto)
                .collect(toList());
    }

    @Transactional
    @Override
    public ParticipationRequestDto confirmRequest(Long userId, Long eventId, Long reqId) {
        Request request = requestRepository.findByIdAndEventId(reqId, eventId)
                .orElseThrow(() -> new ObjectNotFoundException("Нет такой заявки!"));
        Event event = eventRepository.findByIdAndAndInitiator_Id(eventId, userId)
                .orElseThrow(() -> new ObjectNotFoundException("Нет такого события от этого пользователя!"));
        if (event.getParticipantLimit() == 0 || !event.getRequestModeration()) {
            request.setStatus(StateRequest.CONFIRMED);
            event.setConfirmedRequests(event.getConfirmedRequests() + 1);
            eventRepository.save(event);
            return RequestMapper.toParticipationRequestDto(requestRepository.save(request));
        }
        long countRequest = requestRepository.countByEvent_IdAndStatus(eventId, StateRequest.CONFIRMED);
        if (event.getParticipantLimit() <= countRequest) {
            request.setStatus(StateRequest.REJECTED);
            return RequestMapper.toParticipationRequestDto(requestRepository.save(request));
        }
        request.setStatus(StateRequest.CONFIRMED);
        event.setConfirmedRequests(event.getConfirmedRequests() + 1);
        eventRepository.save(event);
        if (event.getParticipantLimit() <= countRequest + 1) {
            List<Request> requestsPending = requestRepository.findByEventIdAndStatus(eventId, StateRequest.PENDING);
            List<Request> requestsRejected = requestsPending
                    .stream()
                    .map(request1 -> {
                        Request req = new Request();
                        req.setRequester(request1.getRequester());
                        req.setEvent(request1.getEvent());
                        req.setId(request1.getId());
                        req.setCreated(request1.getCreated());
                        req.setStatus(StateRequest.REJECTED);
                        return req;
                    })
                    .collect(toList());
//            for (Request requestNew : requestsPending) {
//                requestNew.setStatus(StateRequest.REJECTED);
////                requestRepository.save(requestNew);
//            }
            requestRepository.saveAll(requestsRejected);
        }
        return RequestMapper.toParticipationRequestDto(requestRepository.save(request));
    }

    @Transactional
    @Override
    public ParticipationRequestDto rejectRequest(Long userId, Long eventId, Long reqId) {
        Request request = requestRepository.findRequestOfEvent(reqId, userId, eventId)
                .orElseThrow(() -> new ObjectNotFoundException("Нет такой заявки!"));
        request.setStatus(StateRequest.REJECTED);
        return RequestMapper.toParticipationRequestDto(requestRepository.save(request));
    }

    @Override
    public List<ParticipationRequestDto> findRequestsOfUserFromOtherEvents(Long userId) {
        return requestRepository.findByRequesterId(userId)
                .stream()
                .map(RequestMapper::toParticipationRequestDto)
                .collect(toList());
    }

    @Transactional
    @Override
    public ParticipationRequestDto addNewRequestOfUser(Long userId, Long eventId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ObjectNotFoundException("Нет такого пользователя!"));
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ObjectNotFoundException("Нет такого события!"));
        if (user.getId().equals(event.getInitiator().getId())) {
            throw new ValidationException("Инициатор события не может добавить запрос на участие в своём событии!");
        }
        if (event.getState().equals(StateEvent.PENDING) || event.getState().equals(StateEvent.CANCELED)) {
            throw new ValidationException("Нельзя участвовать в неопубликованном событии!");
        }
        long countRequest = requestRepository.countByEvent_IdAndStatus(eventId, StateRequest.CONFIRMED);
        if (event.getParticipantLimit() == countRequest) {
            throw new ValidationException(" достигнут лимит запросов на участие!");
        }
        if (!event.getRequestModeration()) {
            Request request = RequestMapper.toRequest(user, event, StateRequest.CONFIRMED);
            event.setConfirmedRequests(event.getConfirmedRequests() + 1);
            eventRepository.save(event);
            return RequestMapper.toParticipationRequestDto(requestRepository.save(request));
        }
        Request request = RequestMapper.toRequest(user, event, StateRequest.PENDING);
        return RequestMapper.toParticipationRequestDto(requestRepository.save(request));
    }

    @Transactional
    @Override
    public ParticipationRequestDto cancelRequest(Long userId, Long requestId) {
        Request request = requestRepository.findByIdAndRequesterId(requestId, userId)
                .orElseThrow(() -> new ObjectNotFoundException("Нет такой заявки!"));
        if (request.getStatus().equals(StateRequest.CONFIRMED)) {
            Event event = eventRepository.findById(request.getEvent()
                    .getId()).orElseThrow(() -> new ObjectNotFoundException("Нет такого события!"));
            event.setConfirmedRequests(event.getConfirmedRequests() - 1);
            eventRepository.save(event);
        }
        request.setStatus(StateRequest.CANCELED);
        return RequestMapper.toParticipationRequestDto(requestRepository.save(request));
    }
}
