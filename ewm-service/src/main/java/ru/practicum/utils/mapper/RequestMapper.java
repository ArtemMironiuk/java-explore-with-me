package ru.practicum.utils.mapper;

import ru.practicum.model.Event;
import ru.practicum.model.Request;
import ru.practicum.model.enumstatus.StateRequest;
import ru.practicum.model.User;
import ru.practicum.model.dto.ParticipationRequestDto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class RequestMapper {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");

    public static ParticipationRequestDto toParticipationRequestDto(Request request) {
        return ParticipationRequestDto.builder()
                .created(LocalDateTime.parse(request.getCreated().format(formatter)))
                .event(request.getEvent().getId())
                .id(request.getId())
                .requester(request.getRequester().getId())
                .status(request.getStatus())
                .build();
    }

    public static Request toRequest(User user, Event event, StateRequest state) {
        return Request.builder()
                .requester(user)
                .event(event)
                .created(LocalDateTime.parse(LocalDateTime.now().format(formatter)))
                .status(state)
                .build();
    }
}
