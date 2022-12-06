package ru.practicum.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.exception.EncodingException;
import ru.practicum.exception.LocalDateTimeException;
import ru.practicum.model.EndpointStatsMapper;
import ru.practicum.model.Statistic;
import ru.practicum.model.dto.EndpointHitDto;
import ru.practicum.model.dto.ViewStats;
import ru.practicum.repository.StatsServiceRepository;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Slf4j
@Service
@RequiredArgsConstructor
public class StatsServiceImpl implements StatsService {


    private final StatsServiceRepository statsServiceRepository;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public Statistic save(EndpointHitDto endpointHitDto) {

        Statistic statistic = EndpointStatsMapper.toEndpointHit(endpointHitDto);
        return statsServiceRepository.save(statistic);
    }

    @Override
    public ViewStats[] getViewStats(String start, String end, String[] uris, Boolean unique) {
        try {
            LocalDateTime startDe = LocalDateTime.parse(decode(start), formatter);
            LocalDateTime endDe = LocalDateTime.parse(decode(end), formatter);
            if (unique) {
                return statsServiceRepository.findStatAllUnique(startDe, endDe, uris);
            } else {
                return statsServiceRepository.findStatAll(startDe, endDe, uris);
            }
        } catch (DateTimeParseException e) {
            throw new LocalDateTimeException("Ошибка преобразования типа String в тип LocalDateTime!");
        }
    }

    private String decode(String value) {
        try {
            return URLDecoder.decode(value, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException e) {
            throw new EncodingException("Декодирование символов не поддерживается!");
        }
    }
}
