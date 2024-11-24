package chaekdam.domain.image.presentation.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseMessage {
    NULL_IMAGE("이미지가 없습니다."),
    FAIL_IMAGE_SAVE("이미지 저장에 실패하였습니다.")
    ;
    private String message;
}