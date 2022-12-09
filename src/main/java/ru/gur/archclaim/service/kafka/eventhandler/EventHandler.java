package ru.gur.archclaim.service.kafka.eventhandler;

import ru.gur.archclaim.service.kafka.EventSource;

public interface EventHandler<T extends EventSource> {

    boolean canHandle(EventSource eventSource);

    String handleEvent(T eventSource);
}
