package chaekdam.domain.user.dto.res;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
public class CheckResponse {
    private int status;
    public CheckResponse(int status) {
        this.status = status;
    }
}
