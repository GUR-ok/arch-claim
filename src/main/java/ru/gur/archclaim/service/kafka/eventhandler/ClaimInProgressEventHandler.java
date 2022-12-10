package ru.gur.archclaim.service.kafka.eventhandler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import ru.gur.archclaim.entity.State;
import ru.gur.archclaim.service.ClaimService;
import ru.gur.archclaim.service.kafka.ClaimInProgressEventData;
import ru.gur.archclaim.service.kafka.Event;
import ru.gur.archclaim.service.kafka.EventSource;

@Slf4j
@Component
@RequiredArgsConstructor
public class ClaimInProgressEventHandler implements EventHandler<ClaimInProgressEventData> {

    private final ClaimService claimService;

    @Override
    public boolean canHandle(final EventSource eventSource) {
        Assert.notNull(eventSource, "EventSource must not be null");

        return Event.CLAIM_IN_PROGRESS.equals(eventSource.getEvent());
    }

    @Override
    public String handleEvent(final ClaimInProgressEventData eventSource) {
        claimService.updateStatus(eventSource.getId(), State.IN_PROGRESS);

        return eventSource.getEvent().name();
    }
}
