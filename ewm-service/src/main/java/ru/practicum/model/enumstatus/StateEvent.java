package ru.practicum.model.enumstatus;

import java.util.Optional;

public enum StateEvent {
    PENDING,
    PUBLISHED,
    CANCELED;

    public static Optional<StateEvent> from(String stringEventState) {
        for (StateEvent eventState : values()) {
            if (eventState.name().equalsIgnoreCase(stringEventState)) {
                return Optional.of(eventState);
            }
        }
        return Optional.empty();
    }
}
