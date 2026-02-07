package org.example.be_porfolio.Controller;

import lombok.RequiredArgsConstructor;
import org.example.be_porfolio.DTO.SEO.SeoMetadataDTO;
import org.example.be_porfolio.Service.SeoMetadataService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/seo")
@RequiredArgsConstructor
public class SeoMetadataController {

    private final SeoMetadataService seoService;

    @GetMapping
    public ResponseEntity<SeoMetadataDTO> getSeo(@RequestParam String url) {
        SeoMetadataDTO dto = seoService.getSeoByUrl(url);
        return dto != null ? ResponseEntity.ok(dto) : ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<SeoMetadataDTO> saveSeo(@RequestBody SeoMetadataDTO dto) {
        return ResponseEntity.ok(seoService.saveOrUpdate(dto));
    }
}
