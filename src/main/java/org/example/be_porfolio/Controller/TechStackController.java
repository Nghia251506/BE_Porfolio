package org.example.be_porfolio.Controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.be_porfolio.DTO.TechStack.TechStackRequest;
import org.example.be_porfolio.DTO.TechStack.TechStackResponse;
import org.example.be_porfolio.Service.TechStack.TechStackService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tech-stacks")
@RequiredArgsConstructor
@Tag(name = "Tech Stack", description = "Quản lý danh sách công nghệ (Java, React, v.v.)")
public class TechStackController {

    private final TechStackService techStackService;

    @PostMapping
    @Operation(summary = "Tạo mới công nghệ")
    public ResponseEntity<TechStackResponse> create(@Valid @RequestBody TechStackRequest request) {
        return ResponseEntity.ok(techStackService.create(request));
    }

    @GetMapping
    @Operation(summary = "Lấy tất cả công nghệ")
    public ResponseEntity<List<TechStackResponse>> getAll() {
        return ResponseEntity.ok(techStackService.getAll());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Xóa công nghệ theo ID")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        techStackService.delete(id);
        return ResponseEntity.noContent().build();
    }
}