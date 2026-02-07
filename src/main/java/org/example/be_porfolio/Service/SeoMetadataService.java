package org.example.be_porfolio.Service;

import lombok.RequiredArgsConstructor;
import org.example.be_porfolio.DTO.SEO.SeoMetadataDTO;
import org.example.be_porfolio.Entity.SeoMetadata;
import org.example.be_porfolio.Repository.SeoMetadataRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SeoMetadataService {

    private final SeoMetadataRepository seoRepository;

    public SeoMetadataDTO getSeoByUrl(String url) {
        return seoRepository.findByPageUrl(url)
                .map(this::convertToDTO)
                .orElse(null); // Hoặc trả về Default SEO nếu muốn
    }

    @Transactional
    public SeoMetadataDTO saveOrUpdate(SeoMetadataDTO dto) {
        SeoMetadata entity = seoRepository.findByPageUrl(dto.getPageUrl())
                .orElse(new SeoMetadata());

        // Map từ DTO sang Entity
        entity.setPageUrl(dto.getPageUrl());
        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());
        entity.setKeywords(dto.getKeywords());
        entity.setOgImage(dto.getOgImage());
        entity.setCanonicalUrl(dto.getCanonicalUrl());
        entity.setH1Override(dto.getH1Override());

        SeoMetadata saved = seoRepository.save(entity);
        return convertToDTO(saved);
    }

    private SeoMetadataDTO convertToDTO(SeoMetadata entity) {
        return SeoMetadataDTO.builder()
                .pageUrl(entity.getPageUrl())
                .title(entity.getTitle())
                .description(entity.getDescription())
                .keywords(entity.getKeywords())
                .ogImage(entity.getOgImage())
                .canonicalUrl(entity.getCanonicalUrl())
                .h1Override(entity.getH1Override())
                .build();
    }
}
