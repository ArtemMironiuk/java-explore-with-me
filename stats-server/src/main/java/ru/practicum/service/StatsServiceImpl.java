package ru.practicum.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.model.EndpointHit;
import ru.practicum.model.EndpointHitDto;
import ru.practicum.model.EndpointStatsMapper;
import ru.practicum.model.ViewStats;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class StatsServiceImpl implements StatsService {

    @Autowired
    private StatsServiceRepository statsServiceRepository;

    @Override
    public EndpointHit save(EndpointHitDto endpointHitDto) {

        EndpointHit endpointHit = EndpointStatsMapper.toEndpointHit(endpointHitDto);
        return statsServiceRepository.save(endpointHit);
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
