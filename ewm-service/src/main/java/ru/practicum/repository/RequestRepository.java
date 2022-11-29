package ru.practicum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.practicum.model.Request;

import java.util.List;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {

    @Query("select r from Request r " +
            "join Event e on r.event.id = e.id " +
            "where r.event.id in (:eventId) " +
            "and e.initiator in (:userId)")
    List<Request> findRequestsOfEvent(@Param("userId")Long userId,
                                      @Param("eventId")Long eventId);
}
