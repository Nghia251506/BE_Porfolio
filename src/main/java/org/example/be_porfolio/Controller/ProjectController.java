package org.example.be_porfolio.Controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.be_porfolio.DTO.Project.ProjectRequest;
import org.example.be_porfolio.DTO.Project.ProjectResponse;
import org.example.be_porfolio.Service.Project.ProjectService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
@Tag(name = "Project", description = "Quản lý dự án Portfolio")
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping
    @Operation(summary = "Tạo dự án mới kèm TechStacks và Media")
    public ResponseEntity<ProjectResponse> create(@Valid @RequestBody ProjectRequest request) {
        return ResponseEntity.ok(projectService.createProject(request));
    }

    @GetMapping
    @Operation(summary = "Lấy tất cả dự án (Cho khách - chỉ cái đã publish)")
    public ResponseEntity<List<ProjectResponse>> getAllPublished() {
        return ResponseEntity.ok(projectService.getAllPublishedProjects());
    }

    @GetMapping("/admin/all")
    @Operation(summary = "Lấy tất cả dự án (Cho Admin - bao gồm cả nháp)")
    public ResponseEntity<List<ProjectResponse>> getAllForAdmin() {
        return ResponseEntity.ok(projectService.getAllProjects());
    }

    @GetMapping("/{slug}")
    @Operation(summary = "Lấy chi tiết dự án theo Slug (Dành cho Next.js)")
    public ResponseEntity<ProjectResponse> getBySlug(@PathVariable String slug) {
        return ResponseEntity.ok(projectService.getBySlug(slug));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Cập nhật dự án")
    public ResponseEntity<ProjectResponse> update(@PathVariable Long id, @Valid @RequestBody ProjectRequest request) {
        return ResponseEntity.ok(projectService.updateProject(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Xóa dự án (Tự động xóa sạch Media liên quan)")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        projectService.deleteProject(id);
        return ResponseEntity.noContent().build();
    }
}