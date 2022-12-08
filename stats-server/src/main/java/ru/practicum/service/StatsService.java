package ru.practicum.service;

import ru.practicum.model.Statistic;
import ru.practicum.model.dto.EndpointHitDto;
import ru.practicum.model.dto.ViewStats;

public interface StatsService {
    Statistic save(EndpointHitDto endpointHitDto);

    ViewStats[] getViewStats(String start, String end, String[] uris, Boolean unique);
}
