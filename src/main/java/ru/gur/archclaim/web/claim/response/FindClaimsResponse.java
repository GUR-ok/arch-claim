package ru.gur.archclaim.web.claim.response;

import lombok.Builder;
import lombok.Data;
import ru.gur.archclaim.service.ClaimDto;

import java.util.List;

@Data
@Builder
public class FindClaimsResponse {

    private List<ClaimDto> claims;
}
