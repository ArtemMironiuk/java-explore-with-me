package ru.practicum.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.model.EndpointStatsMapper;
import ru.practicum.model.Statistic;
import ru.practicum.model.dto.EndpointHitDto;
import ru.practicum.model.dto.ViewStats;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@Service
@RequiredArgsConstructor
public class StatsServiceImpl implements StatsService {


    private final StatsServiceRepository statsServiceRepository;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public Statistic save(EndpointHitDto endpointHitDto) {

        Statistic statistic = EndpointStatsMapper.toEndpointHit(endpointHitDto);
        return statsServiceRepository.save(statistic);
    }

    @Override
    public ViewStats[] getViewStats(String start, String end, String[] uris, Boolean unique) {
        LocalDateTime startDe = LocalDateTime.parse(decode(start), formatter);
        LocalDateTime endDe = LocalDateTime.parse(decode(end), formatter);
        if (unique) {
            return statsServiceRepository.findStatAllUnique(startDe, endDe, uris);
        } else {
            return statsServiceRepository.findStatAll(startDe, endDe, uris);
        }
    }

    private String decode(String value) {
        try {
            return URLDecoder.decode(value, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}
