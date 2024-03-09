package eggis0.baram.domain2.certification.dto.req;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public class UpdateUserCertifiRequest {
    private final Boolean isCertification;
}
