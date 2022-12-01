package ru.practicum.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.model.Statistic;
import ru.practicum.model.dto.EndpointHitDto;
import ru.practicum.model.EndpointStatsMapper;
import ru.practicum.model.dto.ViewStats;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class StatsServiceImpl implements StatsService {

    @Autowired
    private StatsServiceRepository statsServiceRepository;

    @Override
    public Statistic save(EndpointHitDto endpointHitDto) {

        Statistic statistic = EndpointStatsMapper.toEndpointHit(endpointHitDto);
        return statsServiceRepository.save(statistic);
    }

    @Override
    public List<ViewStats> getViewStats(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique) {
        if (unique) {
            return statsServiceRepository.findAllUnique(start, end, uris, unique);
        } else {
            return statsServiceRepository.findAll(start, end, uris);
        }
    }
}
