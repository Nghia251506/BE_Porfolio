package org.example.be_porfolio.Entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "projects")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(unique = true, nullable = false)
    private String slug; // VD: "erp-system-v1"

    @Column(columnDefinition = "TEXT")
    private String shortDescription;

    @Column(columnDefinition = "LONGTEXT")
    private String content; // Lưu Markdown nội dung chi tiết ERP

    private String thumbnail;
    private String githubUrl;
    private String demoUrl;

    private boolean isPublished = false;
    private int sortOrder = 0; // Để sắp xếp dự án nào hiện trước

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    // QUAN HỆ 1: QUAN HỆ VỚI TECH STACK (N-N)
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "project_tech_stacks",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "tech_stack_id")
    )
    private Set<TechStack> techStacks;

    // QUAN HỆ 2: QUAN HỆ VỚI MEDIA (1-N)
    // Dùng orphanRemoval để khi xóa project thì ảnh/video đi kèm cũng bay màu luôn
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<ProjectMedia> mediaList = new ArrayList<>();
}