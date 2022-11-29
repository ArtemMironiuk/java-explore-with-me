package ru.practicum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.practicum.model.Event;
import ru.practicum.model.State;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    List<Event> findByInitiator(Long userId, Pageable pageable);

    @Query("select e from Event e " +
            "join User u on e.initiator.id = u.id " +
            "join Category c on e.category.id = c.id " +
            "where (e.initiator.id is null or e.initiator.id in (:users)) " +
            "and (e.state is null or e.state in (:states)) " +
            "and (e.category is null or e.category in (:categories)) " +
            "and (e.eventDate is null or e.eventDate > (:rangeStart)) " +
            "and (e.eventDate is null or e.eventDate < (:rangeEnd)) ")
    List<Event> searchEvent(@Param("users") Long[] users,
                            @Param("states") State[] states,
                            @Param("categories") Long[] categories,
                            @Param("rangeStart") LocalDateTime rangeStart,
                            @Param("rangeEnd") LocalDateTime rangeEnd,
                            Pageable pageable);
}
