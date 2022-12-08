package ru.practicum.model;

import ru.practicum.model.dto.EndpointHitDto;

import java.time.LocalDateTime;

public class EndpointStatsMapper {

    public static Statistic toEndpointHit(EndpointHitDto endpointHitDto) {
        return Statistic.builder()
                .app(endpointHitDto.getApp())
                .uri(endpointHitDto.getUri())
                .ip(endpointHitDto.getIp())
                .timestamp(LocalDateTime.now())
                .build();
    }
}
