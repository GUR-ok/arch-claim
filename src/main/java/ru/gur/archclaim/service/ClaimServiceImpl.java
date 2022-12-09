package ru.gur.archclaim.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.gur.archclaim.entity.Claim;
import ru.gur.archclaim.entity.State;
import ru.gur.archclaim.persistence.ClaimRepository;
import ru.gur.archclaim.web.view.StateTranslator;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ClaimServiceImpl implements ClaimService {

    private final ClaimRepository claimRepository;

    private final StateTranslator stateTranslator;

    @Override
    public List<ClaimDto> findClaims(UUID profileId, String state) {
        final Set<State> stateSet = stateTranslator.inputViewToStates(state);

        return claimRepository.findByProfileIdAndStateIn(profileId, stateSet).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public UUID createClaim(ImmutableCreateClaimRequest immutableCreateClaimRequest) {
        final Claim claim = new Claim();
        claim.setProfileId(immutableCreateClaimRequest.getProfileId());
        claim.setProcessId(immutableCreateClaimRequest.getProcessId());

        claimRepository.save(claim);
        archiveOldClaims(claim);

        return claim.getId();
    }

    @Override
    public ClaimDto findClaim(UUID claimId) {
        final Claim claim = claimRepository.findById(claimId)
                .orElseThrow(() -> new RuntimeException("Claim not found"));

        return convertToDto(claim);
    }

    private void archiveOldClaims(final Claim dupe) {
        final List<Claim> duped = claimRepository.findByProfileIdLocked(dupe.getProfileId());
        duped.stream()
                .filter(c -> !c.getId().equals(dupe.getId()))
                .forEach(c -> c.setState(State.CANCELLED));
    }

    private ClaimDto convertToDto(final Claim claim) {
        return ClaimDto.builder()
                .id(claim.getId())
                .processId(claim.getProcessId())
                .profileId(claim.getProfileId())
                .state(claim.getState())
                .build();
    }
}
