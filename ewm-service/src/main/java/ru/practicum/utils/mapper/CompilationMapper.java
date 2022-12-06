package ru.practicum.utils.mapper;

import ru.practicum.model.Compilation;
import ru.practicum.model.Event;
import ru.practicum.model.dto.compilation.CompilationDto;
import ru.practicum.model.dto.compilation.NewCompilationDto;
import ru.practicum.model.dto.event.EventShortDto;

import java.util.HashSet;
import java.util.Set;

public class CompilationMapper {

    public static Compilation toCompilation(NewCompilationDto newCompilation, Set<Event> events) {
        if (newCompilation.getPinned() == null) {
            newCompilation.setPinned(false);
        }
        return Compilation.builder()
                .events(events)
                .pinned(newCompilation.getPinned())
                .title(newCompilation.getTitle())
                .build();
    }

    public static CompilationDto toCompilationDto(Compilation compilation) {
        Set<CompilationDto.EventShortDto> eventShorts = new HashSet<>();
        for (Event event : compilation.getEvents()) {
            eventShorts.add(toEventShortDto(event));
        }
        return CompilationDto.builder()
                .events(eventShorts)
                .id(compilation.getId())
                .pinned(compilation.getPinned())
                .title(compilation.getTitle())
                .build();
    }

    private static CompilationDto.EventShortDto toEventShortDto(Event event) {
        return CompilationDto.EventShortDto.builder()
                .id(event.getId())
                .annotation(event.getAnnotation())
                .category(CompilationDto.EventShortDto.CategoryDto.builder()
                        .id(event.getCategory().getId())
                        .name(event.getCategory().getName())
                        .build())
                .eventDate(event.getEventDate())
                .initiator(CompilationDto.EventShortDto.UserShortDto.builder()
                        .id(event.getInitiator().getId())
                        .name(event.getInitiator().getName())
                        .build())
                .paid(event.getPaid())
                .title(event.getTitle())
                .build();
    }
}
