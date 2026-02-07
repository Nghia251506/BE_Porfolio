package org.example.be_porfolio.Service.Project;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.be_porfolio.DTO.Project.*;
import org.example.be_porfolio.DTO.TechStack.TechStackResponse;
import org.example.be_porfolio.Entity.Project;
import org.example.be_porfolio.Entity.ProjectMedia;
import org.example.be_porfolio.Entity.TechStack;
import org.example.be_porfolio.Repository.ProjectRepository;
import org.example.be_porfolio.Repository.TechStackRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final TechStackRepository techStackRepository;

    @Transactional
    public ProjectResponse createProject(ProjectRequest request) {
        // 1. Check slug
        if (projectRepository.existsBySlug(request.getSlug())) {
            throw new RuntimeException("Slug đã tồn tại!");
        }

        // 2. Map TechStacks
        List<TechStack> techStacks = techStackRepository.findAllByIdIn(request.getTechStackIds());

        // 3. Tạo Project Entity
        Project project = Project.builder()
                .title(request.getTitle())
                .slug(request.getSlug())
                .shortDescription(request.getShortDescription())
                .content(request.getContent())
                .githubUrl(request.getGithubUrl())
                .demoUrl(request.getDemoUrl())
                .thumbnail(request.getThumbnail())
                .isPublished(request.isPublished())
                .sortOrder(request.getSortOrder())
                .techStacks(new HashSet<>(techStacks))
                .build();

        // 4. Xử lý MediaList (Chuyển từ Request DTO sang Entity)
        if (request.getMediaList() != null && !request.getMediaList().isEmpty()) {
            List<ProjectMedia> mediaEntities = request.getMediaList().stream()
                    .map(mediaReq -> ProjectMedia.builder()
                            .mediaUrl(mediaReq.getMediaUrl())
                            .mediaType(mediaReq.getMediaType())
                            .isThumbnail(mediaReq.isThumbnail())
                            .project(project) // Gán quan hệ ngược lại
                            .build())
                    .collect(Collectors.toList());
            project.setMediaList(mediaEntities);
        }

        // 5. Lưu (Cascade sẽ tự lưu luôn cả danh sách Media)
        Project savedProject = projectRepository.save(project);
        return mapToResponse(savedProject);
    }

    public List<ProjectResponse> getAllPublished() {
        return projectRepository.findByIsPublishedTrueOrderBySortOrderAsc()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public ProjectResponse getBySlug(String slug) {
        Project project = projectRepository.findBySlug(slug)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy dự án"));
        return mapToResponse(project);
    }

    // Hàm Helper để chuyển đổi Entity sang DTO (Tránh loop JSON)
    private ProjectResponse mapToResponse(Project project) {
        return ProjectResponse.builder()
                .id(project.getId())
                .title(project.getTitle())
                .slug(project.getSlug())
                .shortDescription(project.getShortDescription())
                .content(project.getContent())
                .thumbnail(project.getThumbnail())
                .githubUrl(project.getGithubUrl())
                .demoUrl(project.getDemoUrl())
                .isPublished(project.isPublished())
                .sortOrder(project.getSortOrder())
                .createdAt(project.getCreatedAt())
                .updatedAt(project.getUpdatedAt())
                .techStacks(project.getTechStacks().stream()
                        .map(tech -> TechStackResponse.builder()
                                .id(tech.getId())
                                .name(tech.getName())
                                .iconUrl(tech.getIconUrl())
                                .category(tech.getCategory())
                                .build())
                        .collect(Collectors.toList()))
                .mediaList(project.getMediaList().stream()
                        .map(media -> ProjectMediaResponse.builder()
                                .id(media.getId())
                                .mediaUrl(media.getMediaUrl())
                                .mediaType(media.getMediaType())
                                .isThumbnail(media.isThumbnail())
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }

    @Transactional
    public ProjectResponse updateProject(Long id, ProjectRequest request) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy dự án"));

        // Cập nhật các trường cơ bản
        project.setTitle(request.getTitle());
        project.setShortDescription(request.getShortDescription());
        project.setContent(request.getContent());
        project.setGithubUrl(request.getGithubUrl());
        project.setDemoUrl(request.getDemoUrl());
        project.setPublished(request.isPublished());
        project.setSortOrder(request.getSortOrder());

        // Cập nhật TechStacks (Xóa cũ thay mới)
        List<TechStack> newTechStacks = techStackRepository.findAllByIdIn(request.getTechStackIds());
        project.setTechStacks(new java.util.HashSet<>(newTechStacks));

        return mapToResponse(projectRepository.save(project));
    }

    @Transactional
    public void deleteProject(Long id) {
        // Vì có CascadeType.ALL nên nó sẽ tự xóa Media liên quan trong DB
        if (!projectRepository.existsById(id))
            throw new RuntimeException("Dự án không tồn tại");
        projectRepository.deleteById(id);
    }

    // 1. Lấy tất cả dự án (Dùng cho trang Admin - hiện cả những cái chưa Publish)
    public List<ProjectResponse> getAllProjects() {
        return projectRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // 2. Lấy chi tiết dự án theo ID (Dùng khi ấn vào nút "Edit" trên trang Admin)
    public ProjectResponse getProjectById(Long id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Dự án với ID " + id + " không tồn tại!"));
        return mapToResponse(project);
    }

    // 3. Lấy tất cả dự án đã Publish (Dùng cho trang Portfolio bên ngoài)
    public List<ProjectResponse> getAllPublishedProjects() {
        return projectRepository.findByIsPublishedTrueOrderBySortOrderAsc()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // Thêm vào ProjectService.java

    @Transactional
    public void addMediaToProject(Long projectId, List<ProjectMediaRequest> mediaRequests) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Dự án không tồn tại"));

        List<ProjectMedia> mediaList = mediaRequests.stream().map(req ->
                ProjectMedia.builder()
                        .mediaUrl(req.getMediaUrl())
                        .mediaType(req.getMediaType()) // IMAGE hoặc VIDEO
                        .isThumbnail(req.isThumbnail())
                        .project(project)
                        .build()
        ).collect(Collectors.toList());

        project.getMediaList().addAll(mediaList);
        projectRepository.save(project);
    }
    public long countAll() {
        return projectRepository.count();
    }
}