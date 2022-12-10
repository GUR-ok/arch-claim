package ru.gur.archclaim.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.gur.archclaim.entity.Claim;
import ru.gur.archclaim.entity.State;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Repository
public interface ClaimRepository extends JpaRepository<Claim, UUID> {

    @Lock(value = LockModeType.PESSIMISTIC_WRITE)
    @Query("FROM Claim as c WHERE c.profileId = :profileId")
    @QueryHints({@QueryHint(name = "javax.persistence.lock.timeout", value = "5000")})
    List<Claim> findByProfileIdLocked(@Param("profileId") UUID profileId);

    List<Claim> findByProfileIdAndStateIn(UUID profileId, Set<State> states);
}
