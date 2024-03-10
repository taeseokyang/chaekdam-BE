package eggis0.baram.domain.image.application;

import eggis0.baram.domain.image.exception.FailImageSaveException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageService {

    @Value("${image.path}")
    private String IMAGE_PATH;
    private static final String DEFAULT_IMAGE = "default.png";

    public String save(MultipartFile pic) {
        if (pic == null) {
            return DEFAULT_IMAGE;
        }
        UUID uuid = UUID.randomUUID();
        String imageFileName = uuid + pic.getOriginalFilename();
        Path imagePath = Paths.get(IMAGE_PATH + imageFileName);
        try {
            Files.write(imagePath, pic.getBytes());
        } catch (Exception e) {
            throw new FailImageSaveException();
        }
        return imageFileName;
    }
}
