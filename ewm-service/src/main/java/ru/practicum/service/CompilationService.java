package ru.practicum.service;

import ru.practicum.dto.compilation.CompilationDto;
import ru.practicum.dto.compilation.NewCompilationDto;

public interface CompilationService {
    CompilationDto createCompilation(NewCompilationDto newCompilation);

    void deleteCompilation(Long compId);

    void deleteEventOfCompilation(Long compId, Long eventId);

    void addEventOfCompilation(Long compId, Long eventId);

    void unpinCompilation(Long compId);

    void pinCompilation(Long compId);
}
