package ru.practicum.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.model.Statistic;
import ru.practicum.model.dto.EndpointHitDto;
import ru.practicum.model.dto.ViewStats;
import ru.practicum.service.StatsService;

import javax.validation.Valid;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
public class StatsController {
    private final StatsService statsService;

    @PostMapping("/hit")
    public Statistic save(@Valid @RequestBody EndpointHitDto endpointHitDto) {
        log.info("add event in statistic");
        return statsService.save(endpointHitDto);
    }

    @GetMapping("/stats")
    public ViewStats[] getViewStats(
            @RequestParam String start,
            @RequestParam String end,
            @RequestParam(required = false) String[] uris,
            @RequestParam(name = "unique", defaultValue = "false") Boolean unique) {
        log.info("retrieve views");
        return statsService.getViewStats(start, end, uris, unique);
    }
}