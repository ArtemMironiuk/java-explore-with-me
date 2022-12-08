package ru.practicum.service;

import ru.practicum.model.dto.comment.FullCommentDto;
import ru.practicum.model.dto.comment.NewCommentDto;
import ru.practicum.model.dto.comment.UpdateCommentDto;

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
}
