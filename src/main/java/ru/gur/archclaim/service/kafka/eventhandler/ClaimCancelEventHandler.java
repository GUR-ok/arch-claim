package ru.gur.archclaim.service.kafka.eventhandler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import ru.gur.archclaim.entity.State;
import ru.gur.archclaim.service.ClaimService;
import ru.gur.archclaim.service.kafka.ClaimCancelEventData;
import ru.gur.archclaim.service.kafka.Event;
import ru.gur.archclaim.service.kafka.EventSource;

@Slf4j
@Component
@RequiredArgsConstructor
public class ClaimCancelEventHandler implements EventHandler<ClaimCancelEventData> {

    private final ClaimService claimService;

    @Override
    public boolean canHandle(final EventSource eventSource) {
        Assert.notNull(eventSource, "EventSource must not be null");

        return Event.CLAIM_CANCEL.equals(eventSource.getEvent());
    }

    @Override
    public String handleEvent(final ClaimCancelEventData eventSource) {
        claimService.updateStatus(eventSource.getId(), State.CANCELLED);

        return eventSource.getEvent().name();
    }
}
