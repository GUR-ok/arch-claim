package ru.gur.archclaim.service.kafka;

import lombok.Builder;
import lombok.Data;
import ru.gur.archclaim.web.claim.request.UpdateClaimRequest;

import java.util.UUID;

@Data
@Builder
public class ClaimDoneEventData implements KafkaEvent {

    private UUID id;

    private UpdateClaimRequest updateClaimRequest;

    @Override
    public Event getEvent() {
        return Event.CLAIM_DONE;
    }
}
