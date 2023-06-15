package starting.precourse.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import starting.precourse.entity.Image;
import starting.precourse.service.ImageService;

@RestController
@RequestMapping("/image")
public class ImageController {

    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Image> saveImage(@RequestParam(value = "image") MultipartFile file, Image image)
        throws Exception {
        return ResponseEntity.ok(imageService.upload(file, image));
    }
}
