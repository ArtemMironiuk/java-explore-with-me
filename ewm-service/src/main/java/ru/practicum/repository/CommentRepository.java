package ru.practicum.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.model.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("select c from Comment c " +
            "where c.event.id = :eventId " +
            "order by c.created desc ")
    List<Comment> findCommentsByEventId(Long eventId, Pageable pageable);

    int countByEventId(Long eventId);
}
