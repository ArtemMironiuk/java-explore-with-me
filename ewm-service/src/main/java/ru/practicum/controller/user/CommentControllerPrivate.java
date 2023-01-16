package ru.practicum.controller.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.model.dto.comment.FullCommentDto;
import ru.practicum.model.dto.comment.NewCommentDto;
import ru.practicum.model.dto.comment.UpdateCommentDto;
import ru.practicum.service.CommentService;

import javax.validation.Valid;
import javax.validation.constraints.PositiveOrZero;

@RestController
@RequestMapping(path = "/users")
@Slf4j
@RequiredArgsConstructor
public class CommentControllerPrivate {

    private final CommentService commentService;

    @PostMapping("/{userId}/comments")
    public FullCommentDto createComment(@PathVariable @PositiveOrZero Long userId,
                                        @RequestParam @PositiveOrZero Long eventId,
                                        @RequestBody @Valid NewCommentDto comment) {
        log.info("Получен запрос к эндпоинту POST, /users/{userId}/comments");
        return commentService.createComment(userId, eventId, comment);
    }

    @PatchMapping("/{userId}/comments")
    public FullCommentDto updateComment(@PathVariable @PositiveOrZero Long userId,
                                        @RequestBody @Valid UpdateCommentDto updateComment) {
        log.info("Получен запрос к эндпоинту PATCH, /users/{userId}/comments");
        return commentService.updateComment(userId, updateComment);
    }

    @DeleteMapping("/{userId}/comments/{commentId}")
    public void removeComment(@PathVariable @PositiveOrZero Long userId,
                              @PathVariable @PositiveOrZero Long commentId) {
        log.info("Получен запрос к эндпоинту DELETE, /users/{userId}/comments/{commentId}");
        commentService.removeComment(userId, commentId);
    }
}
