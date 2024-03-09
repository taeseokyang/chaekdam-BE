package eggis0.baram.controller;

import eggis0.baram.utils.FilePath;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ImageController {
    @GetMapping("/image/{path}")
    public ResponseEntity<?> returnImage(@PathVariable String path) throws Exception {
        System.out.println(FilePath.IMAGEPATH + path);
        Resource resource = new FileSystemResource(FilePath.IMAGEPATH + path);
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }


}