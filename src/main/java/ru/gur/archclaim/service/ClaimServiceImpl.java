package ru.gur.archclaim.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.gur.archclaim.entity.Claim;
import ru.gur.archclaim.entity.State;
import ru.gur.archclaim.persistence.ClaimRepository;
import ru.gur.archclaim.service.immutable.ImmutableCreateClaimRequest;
import ru.gur.archclaim.service.immutable.ImmutableUpdateClaimRequest;
import ru.gur.archclaim.web.view.StateTranslator;

import java.util.List;
import java.util.Optional;
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
    public List<ClaimDto> findClaims(final UUID profileId, final String state) {
        Assert.notNull(profileId, "profileId must not be null");
        Assert.hasText(state, "state must not be blank");

        final Set<State> stateSet = stateTranslator.inputViewToStates(state);

        return claimRepository.findByProfileIdAndStateIn(profileId, stateSet).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public UUID createClaim(final ImmutableCreateClaimRequest immutableCreateClaimRequest) {
        Assert.notNull(immutableCreateClaimRequest, "immutableCreateClaimRequest must not be null");

        final Claim claim = new Claim();
        claim.setProfileId(immutableCreateClaimRequest.getProfileId());
        claim.setProcessId(immutableCreateClaimRequest.getProcessId());

        claimRepository.save(claim);
        archiveOldClaims(claim);

        return claim.getId();
    }

    @Override
    public ClaimDto findClaim(final UUID claimId) {
        Assert.notNull(claimId, "claimId must not be null");

        final Claim claim = claimRepository.findById(claimId)
                .orElseThrow(() -> new RuntimeException("Claim not found"));

        return convertToDto(claim);
    }

    @Override
    public void updateStatus(final UUID claimId, final State state) {
        Assert.notNull(claimId, "claimId must not be null");
        Assert.notNull(state, "state must not be null");

        final Optional<Claim> claim = claimRepository.findById(claimId);
        claim.ifPresent(c -> c.setState(state));
    }

    @Override
    public void updateClaimDetails(final UUID claimId, final ImmutableUpdateClaimRequest immutableUpdateClaimRequest) {
        Assert.notNull(claimId, "claimId must not be null");
        Assert.notNull(immutableUpdateClaimRequest, "immutableUpdateClaimRequest must not be null");

        final Optional<Claim> claim = claimRepository.findById(claimId);
        claim.ifPresent(c -> this.updateClaim(c, immutableUpdateClaimRequest));
    }

    private void archiveOldClaims(final Claim dupe) {
        Assert.notNull(dupe, "dupe must not be null");

        final List<Claim> duped = claimRepository.findByProfileIdLocked(dupe.getProfileId());
        duped.stream()
                .filter(c -> !c.getId().equals(dupe.getId()))
                .filter(c -> State.NEW.equals(c.getState()))
                .forEach(c -> c.setState(State.CANCELLED));
    }

    private ClaimDto convertToDto(final Claim claim) {
        return ClaimDto.builder()
                .id(claim.getId())
                .processId(claim.getProcessId())
                .profileId(claim.getProfileId())
                .state(claim.getState())
                .updated(claim.getUpdated())
                .agreementNumber(claim.getAgreementNumber())
                .firstName(claim.getFirstName())
                .brokerageAccountId(claim.getBrokerageAccountId())
                .build();
    }

    private void updateClaim(final Claim claim,
                             final ImmutableUpdateClaimRequest immutableUpdateClaimRequest) {
        if (immutableUpdateClaimRequest.getAgreementNumber() != null) {
            claim.setAgreementNumber(immutableUpdateClaimRequest.getAgreementNumber());
        }
        if (immutableUpdateClaimRequest.getFirstName() != null) {
            claim.setFirstName(immutableUpdateClaimRequest.getFirstName());
        }
        if (immutableUpdateClaimRequest.getBrokerageAccountId() != null) {
            claim.setBrokerageAccountId(immutableUpdateClaimRequest.getBrokerageAccountId());
        }
    }
}
