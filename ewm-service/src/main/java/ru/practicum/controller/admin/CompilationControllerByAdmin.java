package ru.practicum.controller.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.compilation.CompilationDto;
import ru.practicum.dto.compilation.NewCompilationDto;
import ru.practicum.dto.user.NewUserRequest;
import ru.practicum.dto.user.UserDto;
import ru.practicum.service.CompilationService;

import javax.validation.Valid;
import javax.validation.constraints.PositiveOrZero;

@RestController
@RequestMapping(path = "/admin/compilations")
@Slf4j
@RequiredArgsConstructor
@Validated
public class CompilationControllerByAdmin {

    @Autowired
    private CompilationService compilationService;

    @PostMapping
    public CompilationDto createCompilation(@RequestBody @Valid NewCompilationDto newCompilation) {
        log.info("Получен запрос к эндпоинту POST, /admin/compilations");
        return compilationService.createCompilation(newCompilation);
    }

    @DeleteMapping("/{compId}")
    public void deleteCompilation(@PathVariable @PositiveOrZero Long compId) {
        log.info("Получен запрос к эндпоинту DELETE, /admin/compilations/{compId}");
        compilationService.deleteCompilation(compId);
    }

    @DeleteMapping("/{compId}/events/{eventId}")
    public void deleteEventOfCompilation(@PathVariable @PositiveOrZero Long compId,
                                  @PathVariable @PositiveOrZero Long eventId) {
        log.info("Получен запрос к эндпоинту DELETE, /admin/compilations/{compId}/events/{eventId}");
        compilationService.deleteEventOfCompilation(compId, eventId);
    }

    @PatchMapping("/{compId}/events/{eventId}")
    public void addEventOfCompilation(@PathVariable @PositiveOrZero Long compId,
                                      @PathVariable @PositiveOrZero Long eventId) {
        log.info("Получен запрос к эндпоинту PATCH, /admin/compilations/{compId}/events/{eventId}");
        compilationService.addEventOfCompilation(compId, eventId);
    }

    @DeleteMapping("/{compId}/pin")
    public void unpinCompilation(@PathVariable @PositiveOrZero Long compId) {
        log.info("Получен запрос к эндпоинту DELETE, /admin/compilations/{compId}/pin");
        compilationService.unpinCompilation(compId);
    }

    @PatchMapping("/{compId}/pin")
    public void pinCompilation(@PathVariable @PositiveOrZero Long compId) {
        log.info("Получен запрос к эндпоинту PATCH, /admin/compilations/{compId}/pin");
        compilationService.pinCompilation(compId);
    }

}
