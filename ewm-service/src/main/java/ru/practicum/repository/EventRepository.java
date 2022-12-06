package ru.practicum.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.practicum.model.Event;
import ru.practicum.model.enumstatus.StateEvent;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    List<Event> findByInitiatorId(Long userId, Pageable pageable);

    @Query("select e from Event e " +
            "where ((:users) is null or e.initiator.id in :users) " +
            "and ((:states) is null or e.state in :states) " +
            "and ((:categories) is null or e.category.id in :categories) " +
            "and (e.eventDate > :rangeStart) " +
            "AND (CAST(:rangeEnd AS date) IS NULL OR e.eventDate <= :rangeEnd)")
    List<Event> searchEvent(List<Long> users,
                            List<StateEvent> states,
                            List<Long> categories,
                            LocalDateTime rangeStart,
                            LocalDateTime rangeEnd,
                            Pageable pageable);

    Optional<Event> findByIdAndAndInitiator_Id(Long eventId, Long userId);

    Optional<Event> findByIdAndState(Long eventId, StateEvent state);

    @Modifying
    @Query("update Event e set e.state = :state , e.publishedOn = :dateNow where e.id = :eventId")
    void setStateAndTimePublish(@Param("eventId") Long eventId,
                                @Param("state") StateEvent state,
                                @Param("dateNow") LocalDateTime dateNow);

    Set<Event> findAllByIdIn(Set<Long> events);

    @Query("select e from Event e where (lower(e.annotation) like lower(concat('%', :text, '%')) or " +
            "lower(e.description) like lower(concat('%', :text, '%')))" +
            "AND ((:categories) IS NULL OR e.category.id IN :categories) " +
            "and e.paid = :paid " +
            "and e.eventDate between :rangeStart and :rangeEnd " +
            "and (:onlyAvailable is false or (e.participantLimit = 0 or e.confirmedRequests < e.participantLimit))")
    List<Event> findEvents(String text,
                           List<Long> categories,
                           Boolean paid,
                           LocalDateTime rangeStart,
                           LocalDateTime rangeEnd,
                           Boolean onlyAvailable,
                           Pageable pageable);
}
