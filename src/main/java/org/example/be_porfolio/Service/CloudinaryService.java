package org.example.be_porfolio.Service;

import com.cloudinary.Cloudinary;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CloudinaryService {
    private final Cloudinary cloudinary;

    public Map upload(MultipartFile file, String folder) {
        try {
            Map params = ObjectUtils.asMap(
                    "folder", "portfolio/" + folder,
                    "resource_type", "auto" // Tự động nhận diện ảnh hoặc video
            );
            return cloudinary.uploader().upload(file.getBytes(), params);
        } catch (IOException e) {
            throw new RuntimeException("Upload thất bại!");
        }
    }
}
