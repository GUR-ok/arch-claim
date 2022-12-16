package ru.gur.archclaim.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import ru.gur.archclaim.AbstractSpringBootTest;
import ru.gur.archclaim.entity.Claim;
import ru.gur.archclaim.persistence.ClaimRepository;

import java.util.List;
import java.util.UUID;

public class ClaimServiceTest extends AbstractSpringBootTest {

    private UUID idStub;

    @Autowired
    ClaimRepository claimRepository;

    @Autowired
    ClaimService claimService;

    @BeforeEach
    void init() {
        final Claim claim = new Claim();
        claim.setProfileId(UUID.randomUUID());
        claim.setFirstName("FirstName");

        claimRepository.save(claim);

        idStub = claim.getId();
    }

    @Test
    @Transactional
    void test1() {
        final ClaimDto actual = claimService.findClaim(idStub);

        Assertions.assertAll(() -> Assertions.assertNotNull(actual),
                () -> Assertions.assertEquals(actual.getId(), idStub),
                () -> Assertions.assertEquals(actual.getFirstName(), "FirstName"));
    }

    @Test
    @Transactional
    void test2() {
        final UUID id_1 = UUID.randomUUID();
        final UUID id_2 = UUID.randomUUID();

        final Claim claim1 = new Claim();
        claim1.setProfileId(id_1);
        final Claim claim2 = new Claim();
        claim2.setProfileId(id_2);
        final Claim claim3 = new Claim();
        claim3.setProfileId(id_1);
        claimRepository.save(claim1);
        claimRepository.save(claim2);
        claimRepository.save(claim3);

        final List<ClaimDto> actual = claimService.findClaims(id_1, "ALL");

        Assertions.assertAll(() -> Assertions.assertNotNull(actual),
                () -> org.assertj.core.api.Assertions.assertThat(actual)
                        .anySatisfy((x) -> {
                            Assertions.assertEquals(x.getId(), claim1.getId());
                        })
                        .anySatisfy((x) -> {
                            Assertions.assertEquals(x.getId(), claim3.getId());
                        })
                        .noneSatisfy((x) -> {
                            Assertions.assertEquals(x.getId(), claim2.getId());
                        })
        );
    }

    @Test
    @Transactional
    void test3() {
        final UUID id_1 = UUID.randomUUID();
        final UUID id_2 = UUID.randomUUID();

        final Claim claim1 = new Claim();
        claim1.setProfileId(id_1);
        final Claim claim2 = new Claim();
        claim2.setProfileId(id_2);
        final Claim claim3 = new Claim();
        claim3.setProfileId(id_1);
        claimRepository.save(claim1);
        claimRepository.save(claim2);
        claimRepository.save(claim3);

        final List<ClaimDto> actual = claimService.findClaims(id_1, "COMPLETED");

        Assertions.assertAll(() -> Assertions.assertNotNull(actual),
                () -> org.assertj.core.api.Assertions.assertThat(actual)
                        .isEmpty()
        );
    }
}
