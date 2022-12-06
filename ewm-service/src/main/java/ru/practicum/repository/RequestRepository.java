package ru.practicum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.practicum.model.Request;
import ru.practicum.model.enumstatus.StateRequest;

import java.util.List;
import java.util.Optional;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {

    @Query("select r from Request r " +
            "join Event e on r.event.id = e.id " +
            "where r.event.id in (:eventId) " +
            "and e.initiator.id in (:userId)")
    List<Request> findRequestsOfEvent(@Param("userId") Long userId,
                                      @Param("eventId") Long eventId);

    Optional<Request> findByIdAndEventId(Long reqId, Long eventId);

    long countByEvent_IdAndStatus(Long eventId, StateRequest stateRequest);

    List<Request> findByEventIdAndStatus(Long eventId, StateRequest stateRequest);

    @Query("select r from Request r " +
            "join Event e on r.event.id = e.id " +
            "join User u on e.initiator.id = u.id " +
            "where r.id in (:reqId) " +
            "and r.event.id in (:eventId) " +
            "and e.initiator.id in (:userId)")
    Optional<Request> findRequestOfEvent(@Param("reqId") Long reqId,
                                         @Param("userId") Long userId,
                                         @Param("eventId") Long eventId);

    List<Request> findByRequesterId(Long userId);

    Optional<Request> findByIdAndRequesterId(Long reqId, Long userId);
}
