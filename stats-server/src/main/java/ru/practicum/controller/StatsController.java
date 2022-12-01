package ru.practicum.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.model.Statistic;
import ru.practicum.model.dto.EndpointHitDto;
import ru.practicum.model.dto.ViewStats;
import ru.practicum.service.StatsService;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
public class StatsController {

    @Autowired
    private StatsService statsService;

    @PostMapping("/hit")
    public Statistic save(@Valid @RequestBody EndpointHitDto endpointHitDto) {
        log.info("add event in statistic");
        return statsService.save(endpointHitDto);
    }

    @GetMapping("/stats")
    public List<ViewStats> getViewStats(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime start,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime end,
            @RequestParam(required = false) List<String> uris,
            @RequestParam(name = "unique",defaultValue = "false") Boolean unique) {
        log.info("retrieve views");
        return statsService.getViewStats(start, end, uris, unique);
    }
}
