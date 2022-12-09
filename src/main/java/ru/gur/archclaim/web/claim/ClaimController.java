package ru.gur.archclaim.web.claim;

import ru.gur.archclaim.service.ClaimDto;
import ru.gur.archclaim.web.claim.request.CreateClaimRequest;
import ru.gur.archclaim.web.claim.response.FindClaimsResponse;

import java.util.UUID;

public interface ClaimController {

    FindClaimsResponse findClaims(UUID profileId, String states);

    UUID createClaim(CreateClaimRequest createClaimRequest);

    ClaimDto findClaim(UUID claimId);
}
