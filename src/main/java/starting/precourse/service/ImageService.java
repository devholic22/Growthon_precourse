package starting.precourse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import starting.precourse.entity.Image;
import starting.precourse.repository.ImageRepository;
import starting.precourse.util.UserUtil;

import java.io.IOException;

@Service
public class ImageService {

    @Autowired
    private S3Uploader s3Uploader;
    private final ImageRepository imageRepository;
    private final UserUtil userUtil;

    public ImageService(ImageRepository imageRepository, UserUtil userUtil) {
        this.imageRepository = imageRepository;
        this.userUtil = userUtil;
    }

    public Image upload(MultipartFile file, Image image) throws IOException {
        if (!file.isEmpty()) {
            String storedFileName = s3Uploader.upload(file, "images");
            image.setUrl(storedFileName);
            image.setOwner(userUtil.getLoggedInUser());
        }
        return imageRepository.save(image);
    }
}
