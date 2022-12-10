package ru.gur.archclaim.service.kafka.eventhandler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import ru.gur.archclaim.entity.State;
import ru.gur.archclaim.service.ClaimService;
import ru.gur.archclaim.service.immutable.ImmutableUpdateClaimRequest;
import ru.gur.archclaim.service.kafka.ClaimDoneEventData;
import ru.gur.archclaim.service.kafka.Event;
import ru.gur.archclaim.service.kafka.EventSource;
import ru.gur.archclaim.web.claim.request.UpdateClaimRequest;

@Slf4j
@Component
@RequiredArgsConstructor
public class ClaimDoneEventHandler implements EventHandler<ClaimDoneEventData> {

    private final ClaimService claimService;

    @Override
    public boolean canHandle(final EventSource eventSource) {
        Assert.notNull(eventSource, "EventSource must not be null");

        return Event.CLAIM_DONE.equals(eventSource.getEvent());
    }

    @Override
    public String handleEvent(final ClaimDoneEventData eventSource) {
        claimService.updateClaimDetails(eventSource.getId(),
                this.convert(eventSource.getUpdateClaimRequest()));
        claimService.updateStatus(eventSource.getId(), State.DONE);

        System.out.println("EVENT handled " + eventSource.getEvent().name());

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
