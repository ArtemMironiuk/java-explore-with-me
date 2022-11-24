package ru.practicum.utils.mapper;

import ru.practicum.model.dto.compilation.CompilationDto;
import ru.practicum.model.dto.compilation.NewCompilationDto;
import ru.practicum.model.dto.event.EventShortDto;
import ru.practicum.model.Compilation;
import ru.practicum.model.Event;

import java.util.ArrayList;
import java.util.List;

public class CompilationMapper {

    public static Compilation toCompilation(NewCompilationDto newCompilation, List<Event> events) {
        if(newCompilation.getPinned() == null) {
            newCompilation.setPinned(false);
        }
        return Compilation.builder()
                .events(events)
                .pinned(newCompilation.getPinned())
                .title(newCompilation.getTitle())
                .build();
    }

    public static CompilationDto toCompilationDto(Compilation compilation) {
        List<EventShortDto> eventShorts= new ArrayList<>();
        for (Event event : compilation.getEvents()) {
            eventShorts.add(EventMapper.toEventShortDto(event));
        }
        return CompilationDto.builder()
                .events(eventShorts)
                .id(compilation.getId())
                .pinned(compilation.getPinned())
                .title(compilation.getTitle())
                .build();
    }
}
