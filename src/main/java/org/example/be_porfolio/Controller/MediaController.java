package org.example.be_porfolio.Controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.be_porfolio.Service.CloudinaryService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/api/media")
@RequiredArgsConstructor
@Tag(name = "Media", description = "Quản lý Upload ảnh/video cho dự án ERP")
public class MediaController {

    private final CloudinaryService cloudinaryService;

    // Thêm consumes để Swagger hiện nút chọn file
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Upload file lên Cloudinary", description = "Dùng để upload ảnh screenshot hoặc video demo dự án")
    public ResponseEntity<Map> uploadFile(
            @RequestPart("file") MultipartFile file, // Dùng @RequestPart cho file nhị phân
            @RequestParam(value = "folder", defaultValue = "projects") String folder) {

        Map result = cloudinaryService.upload(file, folder);
        return ResponseEntity.ok(result);
    }
}