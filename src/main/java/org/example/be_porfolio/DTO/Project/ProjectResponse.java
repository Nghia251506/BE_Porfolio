package org.example.be_porfolio.DTO.Project;

import lombok.Builder;
import lombok.Data;
import org.example.be_porfolio.DTO.TechStack.TechStackResponse;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class ProjectResponse {
    private Long id;
    private String title;
    private String slug;
    private String shortDescription;
    private String content;
    private String thumbnail;
    private String githubUrl;
    private String demoUrl;
    private boolean isPublished;
    private int sortOrder;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Trả về danh sách tech stack chi tiết để FE hiện icon
    private List<TechStackResponse> techStacks;

    // Trả về danh sách ảnh/video demo
    private List<ProjectMediaResponse> mediaList;
}
