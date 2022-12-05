package ru.practicum.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.model.Statistic;
import ru.practicum.model.dto.ViewStats;

import java.time.LocalDateTime;

public interface StatsServiceRepository extends JpaRepository<Statistic, Long> {

    @Query("SELECT new ru.practicum.model.dto.ViewStats(e.app, e.uri, COUNT (e.ip)) " +
            "from Statistic e " +
            "WHERE e.timestamp >= (:start) " +
            "AND e.timestamp <= (:end) " +
            "AND e.uri in (:uris) " +
            "GROUP BY e.app, e.uri")
    ViewStats[] findStatAll(@Param("start") LocalDateTime start,
                            @Param("end") LocalDateTime end,
                            @Param("uris") String[] uris);

    @Query("SELECT new ru.practicum.model.dto.ViewStats(e.app, e.uri, COUNT (DISTINCT e.ip)) from Statistic e " +
            "WHERE e.timestamp > (:start) " +
            "AND e.timestamp < (:end) " +
            "AND e.uri in (:uris)" +
            "GROUP BY e.app, e.uri")
    ViewStats[] findStatAllUnique(@Param("start") LocalDateTime start,
                                  @Param("end") LocalDateTime end,
                                  @Param("uris") String[] uris);
}
