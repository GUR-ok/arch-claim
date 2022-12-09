package ru.gur.archclaim.service;

import ru.gur.archclaim.entity.State;
import ru.gur.archclaim.service.immutable.ImmutableCreateClaimRequest;
import ru.gur.archclaim.service.immutable.ImmutableUpdateClaimRequest;

import java.util.List;
import java.util.UUID;

public interface ClaimService {

    List<ClaimDto> findClaims(UUID profileId, String state);

    UUID createClaim(ImmutableCreateClaimRequest immutableCreateClaimRequest);

    ClaimDto findClaim(UUID claimId);

    void updateStatus(UUID claimId, State state);

    void updateClaimDetails(UUID claimId, ImmutableUpdateClaimRequest immutableUpdateClaimRequest);
}
