package org.example.be_porfolio.DTO.Project;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
public class ProjectRequest {
    private String title;
    private String slug;
    private String shortDescription;
    private String content;
    private String githubUrl;
    private String demoUrl;
    private String thumbnail;
    private boolean isPublished;
    private int sortOrder;

    private List<Long> techStackIds; // Danh sách ID công nghệ

    private List<ProjectMediaRequest> mediaList; // Danh sách ảnh/video demo
}
