package org.example.be_porfolio.Controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.be_porfolio.DTO.Certificate.CertificateRequest;
import org.example.be_porfolio.DTO.Certificate.CertificateResponse;
import org.example.be_porfolio.Service.CertificateService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/certificates")
@RequiredArgsConstructor
@Tag(name = "Certificates")
public class CertificateController {
    private final CertificateService certificateService;

    @PostMapping
    public ResponseEntity<CertificateResponse> create(@RequestBody CertificateRequest req) {
        return ResponseEntity.ok(certificateService.create(req));
    }
    @GetMapping
    public ResponseEntity<List<CertificateResponse>> getAll() {
        return ResponseEntity.ok(certificateService.getAll());
    }
}
