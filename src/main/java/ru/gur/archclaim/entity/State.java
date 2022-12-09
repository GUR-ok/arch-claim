package ru.gur.archclaim.entity;

import java.util.Set;

public enum State {

    NEW,
    REJECTED,
    IN_PROGRESS,
    WAITING_FOR_SIGN,
    CANCELLED,
    DONE;

    public static Set<State> getProcessingStates() {
        return Set.of(NEW, IN_PROGRESS);
    }

    public static Set<State> getCompleted() {
        return Set.of(REJECTED, CANCELLED, DONE);
    }
}
