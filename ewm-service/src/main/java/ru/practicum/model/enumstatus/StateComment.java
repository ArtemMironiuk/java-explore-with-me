package ru.practicum.model.enumstatus;

import java.util.Optional;

public enum StateComment {
    PENDING,
    PUBLISHED,
    REJECTED;

    public static Optional<StateComment> from(String stateComment) {
        for (StateComment commentState : values()) {
            if (commentState.name().equalsIgnoreCase(stateComment)) {
                return java.util.Optional.of(commentState);
            }
        }
        return java.util.Optional.empty();
    }
}
