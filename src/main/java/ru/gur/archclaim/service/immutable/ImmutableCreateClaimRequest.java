package ru.gur.archclaim.service.immutable;

import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Value
@Builder
public class ImmutableCreateClaimRequest {

    String processId;

    UUID profileId;
}
