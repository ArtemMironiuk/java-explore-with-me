package ru.practicum.service;

import ru.practicum.model.dto.comment.FullCommentDto;
import ru.practicum.model.dto.event.*;
import ru.practicum.model.enumstatus.Sort;
import ru.practicum.model.enumstatus.StateEvent;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

public interface EventService {

    /**
     * Поиск событий
     *
     * @param users       список id пользователей, чьи события нужно найти
     * @param stateEvents список состояний в которых находятся искомые события
     * @param categories  список id категорий в которых будет вестись поиск
     * @param rangeStart  дата и время не раньше которых должно произойти событие
     * @param rangeEnd    дата и время не позже которых должно произойти событие
     * @param from        количество событий, которые нужно пропустить для формирования текущего набора
     * @param size        количество событий в наборе
     * @return Список найденных событий
     */
    List<EventFullDto> searchEvents(List<Long> users, List<StateEvent> stateEvents, List<Long> categories,
                                    LocalDateTime rangeStart, LocalDateTime rangeEnd, Integer from, Integer size);

    /**
     * Редактирование события
     *
     * @param eventId      id события
     * @param eventRequest данные для изменения информации о событии
     * @return Отредактированное событие
     */
    EventFullDto updateEventRequest(Long eventId, AdminUpdateEventRequest eventRequest);

    /**
     * Публикация события
     *
     * @param eventId id события
     * @return Опубликованное событие
     */
    EventFullDto publishingEvent(Long eventId);

    /**
     * Отклонение события
     *
     * @param eventId id события
     * @return Отклоненное событие
     */
    EventFullDto rejectionEvent(Long eventId);

    /**
     * Получение событий с возможностью фильтрации
     *
     * @param text          текст для поиска в содержимом аннотации и подробном описании события
     * @param categories    список идентификаторов категорий в которых будет вестись поиск
     * @param paid          поиск только платных/бесплатных событий
     * @param rangeStart    дата и время не раньше которых должно произойти событие
     * @param rangeEnd      дата и время не позже которых должно произойти событие
     * @param onlyAvailable только события у которых не исчерпан лимит запросов на участие
     * @param sort          Вариант сортировки: по дате события или по количеству просмотров
     * @param from          количество событий, которые нужно пропустить для формирования текущего набора
     * @param size          количество событий в наборе
     * @param request       запрос с ip адресом, url
     * @return Список найденных событий
     */
    List<EventShortDto> findEvents(String text, List<Long> categories, Boolean paid, LocalDateTime rangeStart,
                                   LocalDateTime rangeEnd, Boolean onlyAvailable, Sort sort, Integer from, Integer size,
                                   HttpServletRequest request);

    /**
     * Получение подробной информации об опубликованном событии по его идентификатору
     *
     * @param id      идентификатор события
     * @param request запрос с ip адресом, url
     * @return Экземпляр класса
     */
    EventFullDto findEventById(Long id, HttpServletRequest request);

    /**
     * Получение событий, добавленных текущим пользователем
     *
     * @param userId id текущего пользователя
     * @param from   количество элементов, которые нужно пропустить для формирования текущего набора
     * @param size   количество элементов в наборе
     * @return найденное событие
     */
    List<EventShortDto> findEventsOfUser(Long userId, Integer from, Integer size);

    /**
     * Изменение события добавленного текущим пользователем
     *
     * @param userId      id текущего пользователя
     * @param updateEvent новые данные события
     * @return измененное события
     */
    EventFullDto updateEvent(Long userId, UpdateEventRequest updateEvent);

    /**
     * Добавление нового события
     *
     * @param userId   id текущего пользователя
     * @param newEvent данные добавляемого события
     * @return событие, которое добавлено в базу
     */
    EventFullDto addNewEvent(Long userId, NewEventDto newEvent);

    /**
     * Получение новой информации о событии добавленном текущим пользователем
     *
     * @param userId  id текущего пользователя
     * @param eventId id события
     * @return найденное событие
     */
    EventFullDto findEventOfUser(Long userId, Long eventId);

    /**
     * Отмена события добавленного текущим пользователем
     *
     * @param userId  id текущего пользователя
     * @param eventId id отменяемого события
     * @return отмененное событие
     */
    EventFullDto cancelEventOfUser(Long userId, Long eventId);

    /**
     * Получить список комментариев у события
     *
     * @param eventId id события
     * @param from    количество элементов, которые нужно пропустить для формирования текущего набора
     * @param size    количество элементов в наборе
     * @return список комментария
     */
    List<FullCommentDto> findCommentsByEventId(Long eventId, Integer from, Integer size);
}
