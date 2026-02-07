package org.example.be_porfolio.DTO.SEO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SeoMetadataDTO {

    // Key quan trọng nhất để định danh trang (VD: "/", "/projects/1")
    private String pageUrl;

    private String title;
    private String description;
    private String keywords;
    private String ogImage;
    private String canonicalUrl;
    private String h1Override;
}
