package ru.practicum.utils.mapper;

import ru.practicum.model.Comment;
import ru.practicum.model.Event;
import ru.practicum.model.User;
import ru.practicum.model.dto.comment.FullCommentDto;
import ru.practicum.model.dto.comment.NewCommentDto;
import ru.practicum.model.enumstatus.StateComment;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CommentMapper {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");

    public static Comment toComment(NewCommentDto newComment, Event event, User user) {
        return Comment.builder()
                .text(newComment.getText())
                .event(event)
                .author(user)
                .created(LocalDateTime.parse(LocalDateTime.now().format(formatter)))
                .status(StateComment.PENDING)
                .build();
    }

    public static FullCommentDto toFullCommentDto(Comment comment) {
        if (comment == null) {
            return null;
        }
        return FullCommentDto.builder()
                .id(comment.getId())
                .text(comment.getText())
                .authorId(comment.getAuthor().getId())
                .authorName(comment.getAuthor().getName())
                .eventId(comment.getEvent().getId())
                .created(comment.getCreated())
                .status(comment.getStatus())
                .build();
    }
}
