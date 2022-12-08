package ru.practicum.controller.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import ru.practicum.model.dto.comment.FullCommentDto;
import ru.practicum.model.enumstatus.StateComment;
import ru.practicum.service.CommentService;

import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(path = "/admin/comments")
@Slf4j
@RequiredArgsConstructor
public class CommentControllerByAdmin {

    private final CommentService commentService;

    @GetMapping
    public List<FullCommentDto> searchComments(@RequestParam(name = "users", required = false) List<Long> users,
                                               @RequestParam(name = "states", required = false) List<StateComment> states,
                                               @RequestParam(name = "events", required = false) List<Long> events,
                                               @RequestParam(name = "rangeStart", required = false)
                                               @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeStart,
                                               @RequestParam(name = "rangeEnd", required = false)
                                               @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeEnd,
                                               @RequestParam(name = "from", defaultValue = "0") Integer from,
                                               @RequestParam(name = "size", defaultValue = "10") Integer size) {
        log.info("Получен запрос к эндпоинту GET, /admin/comments");
        return commentService.searchComments(users, states, events, rangeStart, rangeEnd, from, size);
    }

    @PatchMapping("/{commentId}/publish")
    public FullCommentDto publishingComment(@PathVariable Long commentId) {
        log.info("Получен запрос к эндпоинту PATCH, /admin/comments/{commentId}/publish");
        return commentService.publishingComment(commentId);
    }

    @PatchMapping("/{commentId}/reject")
    public FullCommentDto rejectionComment(@PathVariable @PositiveOrZero Long commentId) {
        log.info("Получен запрос к эндпоинту PATCH, /admin/comments/{commentId}/reject");
        return commentService.rejectionComment(commentId);
    }
}
