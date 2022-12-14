package ru.practicum.model.dto;

import lombok.*;
import ru.practicum.model.enumstatus.StateRequest;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class ParticipationRequestDto {
    private LocalDateTime created;
    private Long event;
    private Long id;
    private Long requester;
    private StateRequest status;
}
