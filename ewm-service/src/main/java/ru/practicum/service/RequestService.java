package ru.practicum.service;

import ru.practicum.model.dto.ParticipationRequestDto;

import java.util.List;

public interface RequestService {
    /**
     * Получение информации о запросах на участие в событии текущего пользователя
     * @param userId id текущего пользователя
     * @param eventId id события
     * @return найденные запросы на участие
     */
    List<ParticipationRequestDto> findRequestsOfUser(Long userId, Long eventId);

    /**
     * Подтверждение чужой заявки на участие в событии текущего пользователя
     * @param userId id текущего пользователя
     * @param eventId id события текущего пользователя
     * @param reqId id заявки, которую подтверждает текущий пользователь
     * @return подтвержденная заявка
     */
    ParticipationRequestDto confirmRequest(Long userId, Long eventId, Long reqId);

    /**
     * Отклонение чужой заявки на участие в событии текущего пользователя
     * @param userId id текущего пользователя
     * @param eventId id события текущего пользователя
     * @param reqId id заявки, которую отменяет текущий пользователь
     * @return отмененная заявка
     */
    ParticipationRequestDto rejectRequest(Long userId, Long eventId, Long reqId);

    /**
     * Получение информации о заявка текущего пользователя на участие в чужих событиях
     * @param userId id текущего пользователя
     * @return найденные запросы на участие
     */
    List<ParticipationRequestDto> findRequestsOfUserFromOtherEvents(Long userId);

    /**
     * Добавление запроса от текущего пользователя на участие в событии
     * @param userId id текущего пользователя
     * @param eventId id события
     * @return созданная заявка
     */
    ParticipationRequestDto addNewRequestOfUser(Long userId, Long eventId);

    /**
     * Отмена своего запроса на участие в событии
     * @param userId id текущего пользователя
     * @param requestId id запроса на участие
     * @return отмененная заявка
     */
    ParticipationRequestDto cancelRequest(Long userId, Long requestId);
}
