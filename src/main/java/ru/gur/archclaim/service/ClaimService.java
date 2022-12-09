package ru.gur.archclaim.service;

import java.util.List;
import java.util.UUID;

public interface ClaimService {

    List<ClaimDto> findClaims(UUID profileId, String state);

    UUID createClaim(ImmutableCreateClaimRequest immutableCreateClaimRequest);

    ClaimDto findClaim(UUID claimId);
}
