package ru.practicum.utils.mapper;

import ru.practicum.model.Location;

public class LocationMapper {
    public static Location toLocation(Location location) {
        return Location.builder()
                .lon(location.getLon())
                .lat(location.getLat())
                .build();
    }
}
