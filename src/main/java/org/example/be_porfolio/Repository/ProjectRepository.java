package org.example.be_porfolio.Repository;

import org.example.be_porfolio.Entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    // Tìm theo slug cho Next.js render chi tiết
    Optional<Project> findBySlug(String slug);

    // Check trùng slug khi tạo/sửa
    boolean existsBySlug(String slug);

    // Lấy danh sách dự án ra trang chủ (chỉ lấy cái đã publish và theo thứ tự ưu tiên)
    List<Project> findByIsPublishedTrueOrderBySortOrderAsc();

    // Tìm kiếm dự án theo tiêu đề (cho thanh search trên Portfolio)
    List<Project> findByTitleContainingIgnoreCase(String title);
}