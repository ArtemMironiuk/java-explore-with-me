package ru.practicum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.practicum.model.Event;
import ru.practicum.model.StateEvent;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    List<Event> findByInitiator(Long userId, Pageable pageable);

    @Query("select e from Event e " +
            "join User u on e.initiator.id = u.id " +
            "join Category c on e.category.id = c.id " +
            "where (e.initiator.id is null or e.initiator.id in (:users)) " +
            "and (e.stateEvent is null or e.stateEvent in (:states)) " +
            "and (e.category is null or e.category in (:categories)) " +
            "and (e.eventDate is null or e.eventDate > (:rangeStart)) " +
            "and (e.eventDate is null or e.eventDate < (:rangeEnd)) ")
    List<Event> searchEvent(@Param("users") Long[] users,
                            @Param("states") StateEvent[] stateEvents,
                            @Param("categories") Long[] categories,
                            @Param("rangeStart") LocalDateTime rangeStart,
                            @Param("rangeEnd") LocalDateTime rangeEnd,
                            Pageable pageable);

    Optional<Event> findByIdAndAndInitiator_Id(Long eventId, Long userId);

    Optional<Event> findByIdAndStateEvent(Long eventId, StateEvent state);
}
