package ru.practicum.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.handler.exception.ObjectNotFoundException;
import ru.practicum.model.Compilation;
import ru.practicum.model.Event;
import ru.practicum.model.dto.compilation.CompilationDto;
import ru.practicum.model.dto.compilation.NewCompilationDto;
import ru.practicum.repository.CompilationRepository;
import ru.practicum.repository.EventRepository;
import ru.practicum.service.CompilationService;
import ru.practicum.utils.mapper.CompilationMapper;

import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CompilationServiceImpl implements CompilationService {

    private final CompilationRepository compilationRepository;
    private final EventRepository eventRepository;

    @Transactional
    @Override
    public CompilationDto createCompilation(NewCompilationDto newCompilation) {
        Set<Event> events = eventRepository.findAllByIdIn(newCompilation.getEvents());
        Compilation compilation = compilationRepository.save(CompilationMapper.toCompilation(newCompilation, events));
        return CompilationMapper.toCompilationDto(compilation);
    }

    @Transactional
    @Override
    public void deleteCompilation(Long compId) {
        compilationRepository.findById(compId)
                .orElseThrow(() -> new ObjectNotFoundException("Нет такой подборки!"));
        compilationRepository.deleteById(compId);
    }

    @Transactional
    @Override
    public void deleteEventOfCompilation(Long compId, Long eventId) {
        Compilation compilation = compilationRepository.findById(compId)
                .orElseThrow(() -> new ObjectNotFoundException("Нет такой подборки!"));
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ObjectNotFoundException("Нет такого события!"));
        Set<Event> events = compilation.getEvents();
        events.remove(event);
        compilationRepository.save(compilation);
    }

    @Transactional
    @Override
    public void addEventOfCompilation(Long compId, Long eventId) {
        Compilation compilation = compilationRepository.findById(compId)
                .orElseThrow(() -> new ObjectNotFoundException("Нет такой подборки!"));
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ObjectNotFoundException("Нет такого события!"));
        compilation.getEvents().add(event);
        compilationRepository.save(compilation);
    }

    @Transactional
    @Override
    public void unpinCompilation(Long compId) {
        Compilation compilation = compilationRepository.findById(compId)
                .orElseThrow(() -> new ObjectNotFoundException("Нет такой подборки!"));
        if (compilation.getPinned().equals(true)) {
            compilation.setPinned(false);
        }
        compilationRepository.save(compilation);
    }

    @Transactional
    @Override
    public void pinCompilation(Long compId) {
        Compilation compilation = compilationRepository.findById(compId)
                .orElseThrow(() -> new ObjectNotFoundException("Нет такой подборки!"));
        if (compilation.getPinned().equals(false)) {
            compilation.setPinned(true);
        }
        compilationRepository.save(compilation);
    }

    @Override
    public List<CompilationDto> findCompilations(Boolean pinned, Integer from, Integer size) {
        Pageable pageable = PageRequest.of(from / size, size);
        if (pinned == null) {
            return compilationRepository.findAll(pageable)
                    .stream()
                    .map(CompilationMapper::toCompilationDto)
                    .collect(toList());
        }
        return compilationRepository.findByPinned(pinned, pageable)
                .stream()
                .map(CompilationMapper::toCompilationDto)
                .collect(toList());
    }

    @Override
    public CompilationDto findCompilationById(Long compId) {
        return CompilationMapper.toCompilationDto(compilationRepository.findById(compId)
                .orElseThrow(() -> new ObjectNotFoundException("Нет такой подборки!")));
    }
}
