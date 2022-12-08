package ru.practicum.controller.notuser;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.model.dto.compilation.CompilationDto;
import ru.practicum.service.CompilationService;

import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequestMapping(path = "/compilations")
@Slf4j
@RequiredArgsConstructor
@Validated
public class CompilationControllerPublic {

    private final CompilationService compilationService;

    @GetMapping
    public List<CompilationDto> findCompilations(@RequestParam(name = "pinned", required = false) Boolean pinned,
                                                 @RequestParam(name = "from", defaultValue = "0") Integer from,
                                                 @RequestParam(name = "size", defaultValue = "10") Integer size) {
        log.info("Получен запрос к эндпоинту GET, /compilations");
        return compilationService.findCompilations(pinned, from, size);
    }

    @GetMapping("/{compId}")
    public CompilationDto findCompilationById(@PathVariable @PositiveOrZero Long compId) {
        log.info("Получен запрос к эндпоинту GET, /compilations/compId");
        return compilationService.findCompilationById(compId);
    }
}
