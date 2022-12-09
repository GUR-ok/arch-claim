package ru.gur.archclaim.service.kafka.eventhandler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import ru.gur.archclaim.service.ClaimService;
import ru.gur.archclaim.service.immutable.ImmutableUpdateClaimRequest;
import ru.gur.archclaim.service.kafka.ClaimUpdateEventData;
import ru.gur.archclaim.service.kafka.Event;
import ru.gur.archclaim.service.kafka.EventSource;
import ru.gur.archclaim.web.claim.request.UpdateClaimRequest;

@Slf4j
@Component
@RequiredArgsConstructor
public class ClaimUpdateEventHandler implements EventHandler<ClaimUpdateEventData> {

    private final ClaimService claimService;

    @Override
    public boolean canHandle(final EventSource eventSource) {
        Assert.notNull(eventSource, "EventSource must not be null");

        return Event.UPDATE_CLAIM.equals(eventSource.getEvent());
    }

    @Override
    public String handleEvent(final ClaimUpdateEventData eventSource) {
        claimService.updateClaimDetails(eventSource.getId(),
                this.convert(eventSource.getUpdateClaimRequest()));

        return eventSource.getEvent().name();
    }

    private ImmutableUpdateClaimRequest convert(UpdateClaimRequest updateClaimRequest) {
        return ImmutableUpdateClaimRequest.builder()
                .agreementNumber(updateClaimRequest.getAgreementNumber())
                .brokerageAccountId(updateClaimRequest.getBrokerageAccountId())
                .firstName(updateClaimRequest.getFirstName())
                .build();
    }
}
