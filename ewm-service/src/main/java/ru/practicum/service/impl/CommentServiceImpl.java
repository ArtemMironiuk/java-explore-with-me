package ru.practicum.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.handler.exception.ObjectNotFoundException;
import ru.practicum.handler.exception.ValidationException;
import ru.practicum.model.Comment;
import ru.practicum.model.Event;
import ru.practicum.model.User;
import ru.practicum.model.dto.comment.FullCommentDto;
import ru.practicum.model.dto.comment.NewCommentDto;
import ru.practicum.model.dto.comment.UpdateCommentDto;
import ru.practicum.model.enumstatus.StateComment;
import ru.practicum.repository.CommentRepository;
import ru.practicum.repository.EventRepository;
import ru.practicum.repository.UserRepository;
import ru.practicum.service.CommentService;
import ru.practicum.utils.mapper.CommentMapper;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;


    @Transactional
    @Override
    public FullCommentDto createComment(Long userId, Long eventId, NewCommentDto newComment) {
        User author = userRepository.findById(userId)
                .orElseThrow(() -> new ObjectNotFoundException("Нет такого пользователя!"));
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ObjectNotFoundException("Нет такого события!"));
        Comment comment = CommentMapper.toComment(newComment, event, author);
        return CommentMapper.toFullCommentDto(commentRepository.save(comment));
    }

    @Transactional
    @Override
    public FullCommentDto updateComment(Long userId, UpdateCommentDto updateComment) {
        userRepository.findById(userId)
                .orElseThrow(() -> new ObjectNotFoundException("Нет такого пользователя!"));
        Comment comment = commentRepository.findById(updateComment.getId())
                .orElseThrow(() -> new ObjectNotFoundException("Нет такого комментария!"));
        if (!userId.equals(comment.getAuthor().getId())) {
            throw new ValidationException("Пользователь не является автором комментария!");
        }
        Duration duration = Duration.between(LocalDateTime.now(), comment.getCreated());
        if (duration.toMinutes() >= 15) {
            throw new ValidationException("Невозможно редактировать комментарий, прошло более 15 минут!");
        }
        comment.setText(updateComment.getText());
        return CommentMapper.toFullCommentDto(comment);
    }

    @Transactional
    @Override
    public void removeComment(Long userId, Long commentId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new ObjectNotFoundException("Нет такого пользователя!"));
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ObjectNotFoundException("Нет такого комментария!"));
        if (!userId.equals(comment.getAuthor().getId())) {
            throw new ValidationException("Пользователь не является автором комментария!");
        }
        commentRepository.deleteById(commentId);
    }

    @Override
    public List<FullCommentDto> searchComments(List<Long> users, List<StateComment> states, List<Long> events,
                                               LocalDateTime rangeStart, LocalDateTime rangeEnd, Integer from, Integer size) {
        Pageable pageable = PageRequest.of(from / size, size);
        return commentRepository.searchComments(users, states, events, rangeStart, rangeEnd, pageable)
                .stream()
                .map(CommentMapper::toFullCommentDto)
                .collect(toList());
    }

    @Transactional
    @Override
    public FullCommentDto publishingComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ObjectNotFoundException("Нет такого комментария!"));
        if (comment.getStatus().equals(StateComment.PUBLISHED) || comment.getStatus().equals(StateComment.REJECTED)) {
            throw new ValidationException("Комментарий уже отклонен или опубликован!");
        }
        comment.setStatus(StateComment.PUBLISHED);
        return CommentMapper.toFullCommentDto(commentRepository.save(comment));
    }

    @Override
    public FullCommentDto rejectionComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ObjectNotFoundException("Нет такого комментария!"));
        comment.setStatus(StateComment.REJECTED);
        return CommentMapper.toFullCommentDto(commentRepository.save(comment));
    }
}
