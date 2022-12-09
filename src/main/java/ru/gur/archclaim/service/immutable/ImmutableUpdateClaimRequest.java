package ru.gur.archclaim.service.immutable;

import lombok.Builder;
import lombok.Data;
import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Value
@Builder
public class ImmutableUpdateClaimRequest {

    Long agreementNumber;

    UUID brokerageAccountId;

    String firstName;
}
