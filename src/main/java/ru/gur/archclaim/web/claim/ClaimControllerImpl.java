package ru.gur.archclaim.web.claim;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.gur.archclaim.service.ClaimDto;
import ru.gur.archclaim.service.ClaimService;
import ru.gur.archclaim.service.immutable.ImmutableCreateClaimRequest;
import ru.gur.archclaim.web.claim.request.CreateClaimRequest;
import ru.gur.archclaim.web.claim.response.FindClaimsResponse;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class ClaimControllerImpl implements ClaimController {

    private final ClaimService claimService;

    @Override
    @GetMapping(path = "/claims/{profileId}")
    public FindClaimsResponse findClaims(@PathVariable final UUID profileId,
                                         @RequestParam final String states) {
        return FindClaimsResponse.builder()
                .claims(claimService.findClaims(profileId, states))
                .build();
    }

    @Override
    @PostMapping(path = "/claims")
    public UUID createClaim(@Valid @NotNull @RequestBody final CreateClaimRequest createClaimRequest) {
        return claimService.createClaim(ImmutableCreateClaimRequest.builder()
                .processId(createClaimRequest.getProcessId())
                .profileId(createClaimRequest.getProfileId())
                .build());
    }

    @Override
    @GetMapping(path = "/claim/{claimId}")
    public ClaimDto findClaim(@PathVariable final UUID claimId) {
        return claimService.findClaim(claimId);
    }
}
