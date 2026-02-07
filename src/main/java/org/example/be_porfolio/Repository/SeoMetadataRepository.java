package org.example.be_porfolio.Repository;

import org.example.be_porfolio.Entity.SeoMetadata;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface SeoMetadataRepository extends JpaRepository<SeoMetadata, Long> {

    /**
     * Tìm cấu hình SEO dựa trên đường dẫn URL.
     * @param pageUrl ví dụ: "/", "/projects/1", "/certificates/2"
     * @return Optional chứa dữ liệu SEO nếu tìm thấy
     */
    Optional<SeoMetadata> findByPageUrl(String pageUrl);

    /**
     * Kiểm tra xem URL này đã được cấu hình SEO chưa
     */
    boolean existsByPageUrl(String pageUrl);
}