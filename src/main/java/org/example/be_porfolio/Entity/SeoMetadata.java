package org.example.be_porfolio.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "seo_metadata", indexes = {
        @Index(name = "idx_page_url", columnList = "pageUrl") // Đánh index để truy vấn theo URL cực nhanh
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SeoMetadata {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "page_url", unique = true, nullable = false)
    private String pageUrl; // VD: "/", "/projects/1", "/certificates/2"

    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String keywords;

    @Column(name = "og_image")
    private String ogImage;

    @Column(name = "canonical_url")
    private String canonicalUrl; // Link gốc chuẩn SEO (VD: https://nghiafullstack.xyz/projects/1)

    @Column(name = "h1_override")
    private String h1Override; // Dùng để can thiệp vào SEO Header nếu cần
}