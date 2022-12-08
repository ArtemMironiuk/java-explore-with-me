package ru.practicum.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.model.Comment;
import ru.practicum.model.enumstatus.StateComment;

import java.time.LocalDateTime;
import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("select c from Comment c " +
            "where c.event.id = :eventId " +
            "order by c.created desc ")
    List<Comment> findCommentsByEventId(Long eventId, Pageable pageable);

    int countByEventIdAndStatus(Long eventId, StateComment status);

    @Query("select c from Comment c " +
            "where ((:users) is null or c.author.id in :users) " +
            "and ((:states) is null or c.status in :states) " +
            "and ((:events) is null or c.event.id in :events) " +
            "and (c.created > :rangeStart) " +
            "AND (CAST(:rangeEnd AS date) IS NULL OR c.created <= :rangeEnd)")
    List<Comment> searchComments(List<Long> users,
                                 List<StateComment> states,
                                 List<Long> events,
                                 LocalDateTime rangeStart,
                                 LocalDateTime rangeEnd,
                                 Pageable pageable);
}
