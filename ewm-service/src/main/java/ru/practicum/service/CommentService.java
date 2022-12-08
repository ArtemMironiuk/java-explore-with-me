package ru.practicum.service;

import ru.practicum.model.dto.comment.FullCommentDto;
import ru.practicum.model.dto.comment.NewCommentDto;
import ru.practicum.model.dto.comment.UpdateCommentDto;
import ru.practicum.model.enumstatus.StateComment;

import java.time.LocalDateTime;
import java.util.List;

public interface CommentService {
    /**
     * Добавление комментария текущего пользователя к событию
     *
     * @param userId  id текущего пользователя
     * @param eventId id события
     * @param comment экземпляр класса добавляемого комментария
     * @return добавленный комментарий
     */
    FullCommentDto createComment(Long userId, Long eventId, NewCommentDto comment);

    /**
     * Редактирование своего комментария текущим пользователем
     *
     * @param userId        id текущего пользователя
     * @param updateComment данные для обновления комментария
     * @return обновленный комментарий
     */
    FullCommentDto updateComment(Long userId, UpdateCommentDto updateComment);

    /**
     * Удаление своего комментария текущим пользователем
     *
     * @param userId    id текущего пользователя
     * @param commentId id комментария
     */
    void removeComment(Long userId, Long commentId);

    /**
     * Поиск комментария по параметрам
     *
     * @param users      список id пользователей(комментирующие)
     * @param states     список статусов
     * @param events     список id событий
     * @param rangeStart дата и время не раньше которых должен быть создан комментарий
     * @param rangeEnd   дата и время не позже которых должен быть создан комментарий
     * @param from       количество событий, которые нужно пропустить для формирования текущего набора
     * @param size       количество событий в наборе
     * @return список найденных комментариев
     */
    List<FullCommentDto> searchComments(List<Long> users, List<StateComment> states, List<Long> events,
                                        LocalDateTime rangeStart, LocalDateTime rangeEnd, Integer from, Integer size);

    /**
     * Публикация комментария
     *
     * @param commentId id комментария
     * @return опубликованный комментарий
     */
    FullCommentDto publishingComment(Long commentId);

    /**
     * Отклонение комментария
     *
     * @param commentId id комментария
     * @return отклоненный комментарий
     */
    FullCommentDto rejectionComment(Long commentId);
}
