package ru.practicum.service;

import ru.practicum.model.dto.compilation.CompilationDto;
import ru.practicum.model.dto.compilation.NewCompilationDto;

import java.util.List;

public interface CompilationService {

    /**
     * Добавление новой подборки событий
     * @param newCompilation новая подборка
     * @return Подборка добавленная в базу данных
     */
    CompilationDto createCompilation(NewCompilationDto newCompilation);

    /**
     * Удаление подборки
     * @param compId идентификатор подборки
     */
    void deleteCompilation(Long compId);

    /**
     * Удаление события из подборки
     * @param compId идентификатор подборки
     * @param eventId идентификатор события
     */
    void deleteEventOfCompilation(Long compId, Long eventId);

    /**
     * Добавление события в подборку
     * @param compId идентификатор подборки
     * @param eventId идентификатор события
     */
    void addEventOfCompilation(Long compId, Long eventId);

    /**
     * Открепить подборку с главной страницы
     * @param compId идентификатор подборки
     */
    void unpinCompilation(Long compId);

    /**
     * Закрепить подборку на главной страницы
     * @param compId идентификатор подборки
     */
    void pinCompilation(Long compId);

    /**
     * Получение подборок событий
     * @param pinned закрепленные/не закрепленные подборки
     * @param from количество элементов, которые нужно пропустить для формирования текущего набора
     * @param size количество элементов в наборе
     * @return Подборки событий
     */
    List<CompilationDto> findCompilations(Boolean pinned, Integer from, Integer size);

    /**
     * Получение подборки событий по её id
     * @param compId идентификатор подборки
     * @return Подборка событий
     */
    CompilationDto findCompilationById(Long compId);

}
