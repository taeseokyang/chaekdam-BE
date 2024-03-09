package eggis0.baram.domain.image.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ImageController {
    @Value("${image.path}")
    private String IMAGE_PATH;

    @GetMapping("/image/{path}")
    public ResponseEntity<?> returnImage(@PathVariable String path) throws Exception {
        Resource resource = new FileSystemResource(IMAGE_PATH + path);
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }
}