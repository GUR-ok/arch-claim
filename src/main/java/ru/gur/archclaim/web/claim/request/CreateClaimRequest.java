package ru.gur.archclaim.web.claim.request;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@Builder
public class CreateClaimRequest {

    @NotBlank
    private String processId;

    @NotNull
    private UUID profileId;
}
