package ru.gur.archclaim.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.envers.Audited;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;

@Audited
@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "claims")
public class Claim {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(columnDefinition = "uuid", name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(columnDefinition = "uuid", name = "profile_id", updatable = false, nullable = false)
    private UUID profileId;

    @Column(name = "process_id")
    private String processId;

    @Column(name = "state")
    @Enumerated(EnumType.STRING)
    private State state;

    @Column(name = "agreement_number")
    Long agreementNumber;

    @Column(columnDefinition = "uuid", name = "brokerage_account_id")
    UUID brokerageAccountId;

    @Column(name = "first_name")
    String firstName;

    @UpdateTimestamp
    @Column(name = "updated", nullable = false)
    LocalDateTime updated;

    @PrePersist
    public void prePersist() {
        setState(State.NEW);
    }
}
