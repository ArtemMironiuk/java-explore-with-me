package ru.practicum.service;

import ru.practicum.model.Statistic;
import ru.practicum.model.dto.EndpointHitDto;
import ru.practicum.model.dto.ViewStats;

import java.time.LocalDateTime;
import java.util.List;

public interface StatsService {
    Statistic save(EndpointHitDto endpointHitDto);

    List<ViewStats> getViewStats(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique);
}
