package ru.gur.archclaim.service.kafka;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class ClaimRejectedEventData implements KafkaEvent {

    private UUID id;

    @Override
    public Event getEvent() {
        return Event.CLAIM_REJECTED;
    }
}
