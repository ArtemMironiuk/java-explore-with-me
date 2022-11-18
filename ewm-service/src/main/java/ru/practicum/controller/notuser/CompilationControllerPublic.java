package ru.practicum.controller.notuser;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.PositiveOrZero;

@RestController
@RequestMapping(path = "/compilations")
@Slf4j
@RequiredArgsConstructor
@Validated
public class CompilationControllerPublic {

    private final CompilationService compilationService;

    @GetMapping
    public List<CompilationDto> findCompilations(@RequestParam(name = "pinned") Boolean pinned,
                                                 @RequestParam(name = "from", defaultValue = "0") Integer from,
                                                 @RequestParam(name = "size", defaultValue = "10") Integer size) {
        log.info("Получен запрос к эндпоинту GET, /categories");
        return compilationService.findCompilations(from, size);
    }

    @GetMapping("/{compId}")
    public CompilationDto findCompilationById(@PathVariable @PositiveOrZero Long compId) {
        log.info("Получен запрос к эндпоинту GET, /categories/comId");
        return compilationService.findCompilationById(compId);
    }
}
