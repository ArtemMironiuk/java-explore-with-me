package ru.practicum.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.dto.compilation.CompilationDto;
import ru.practicum.dto.compilation.NewCompilationDto;
import ru.practicum.handler.exception.ObjectNotFoundException;
import ru.practicum.mapper.CompilationMapper;
import ru.practicum.model.Compilation;
import ru.practicum.model.Event;
import ru.practicum.repository.CompilationRepository;
import ru.practicum.repository.EventRepository;
import ru.practicum.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CompilationServiceImpl implements CompilationService {

    @Autowired
    private CompilationRepository compilationRepository;
    @Autowired
    private EventRepository eventRepository;

    @Transactional
    @Override
    public CompilationDto createCompilation(NewCompilationDto newCompilation) {
        List<Event> events = new ArrayList<>();
        for (Long eventId: newCompilation.getEvents()) {
            Event event = eventRepository.findById(eventId).get();
            events.add(event);
        }
        Compilation compilation = compilationRepository.save(CompilationMapper.toCompilation(newCompilation, events));
        return CompilationMapper.toCompilationDto(compilation);
    }

    @Override
    public void deleteCompilation(Long compId) {
        compilationRepository.findById(compId)
                .orElseThrow(() -> new ObjectNotFoundException("Нет такой подборки!"));
        //TODO exception
        compilationRepository.deleteById(compId);
    }

    @Override
    public void deleteEventOfCompilation(Long compId, Long eventId) {

    }

    @Override
    public void addEventOfCompilation(Long compId, Long eventId) {

    }

    @Override
    public void unpinCompilation(Long compId) {

    }

    @Override
    public void pinCompilation(Long compId) {

    }
}
