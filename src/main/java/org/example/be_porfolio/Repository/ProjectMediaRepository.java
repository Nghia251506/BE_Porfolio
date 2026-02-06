package org.example.be_porfolio.Repository;

import org.example.be_porfolio.Entity.ProjectMedia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectMediaRepository extends JpaRepository<ProjectMedia, Long> {
    // Tìm tất cả ảnh/video của 1 dự án
    List<ProjectMedia> findByProjectId(Long projectId);

    // Xóa sạch media của 1 project (dùng khi ông muốn thay máu toàn bộ ảnh demo)
    void deleteByProjectId(Long projectId);
}