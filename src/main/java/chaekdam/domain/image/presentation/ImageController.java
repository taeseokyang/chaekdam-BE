package chaekdam.domain.image.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ImageController {
    @Value("${image.path}")
    private String IMAGE_PATH;

    // 이미지 가져오기
    @GetMapping("/image/{path}")
    public ResponseEntity<?> returnImage(@PathVariable String path) {
        Resource resource = new FileSystemResource(IMAGE_PATH + path);
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }
}