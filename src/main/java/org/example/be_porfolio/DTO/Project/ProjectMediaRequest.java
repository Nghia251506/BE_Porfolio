package org.example.be_porfolio.DTO.Project;

import org.example.be_porfolio.Entity.MediaType; // IMAGE hoặc VIDEO
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectMediaRequest {

    private String mediaUrl;

    private MediaType mediaType; // Ông chọn IMAGE hoặc VIDEO

    private boolean isThumbnail; // Đánh dấu cái nào là ảnh đại diện trong gallery
}