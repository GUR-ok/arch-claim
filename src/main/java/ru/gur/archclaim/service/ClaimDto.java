package ru.gur.archclaim.service;

import lombok.Builder;
import lombok.Value;
import ru.gur.archclaim.entity.State;

import java.util.UUID;

@Value
@Builder
public class ClaimDto {

    UUID id;

    UUID profileId;

    String processId;

    State state;
}
