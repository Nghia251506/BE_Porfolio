package org.example.be_porfolio.Service;

import lombok.RequiredArgsConstructor;
import org.example.be_porfolio.DTO.Certificate.CertificateRequest;
import org.example.be_porfolio.DTO.Certificate.CertificateResponse;
import org.example.be_porfolio.Entity.Certificate;
import org.example.be_porfolio.Repository.CertificateRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CertificateService {
    private final CertificateRepository certificateRepository;

    public CertificateResponse create(CertificateRequest request) {
        Certificate cert = Certificate.builder()
                .name(request.getName())
                .organization(request.getOrganization())
                .issueDate(request.getIssueDate())
                .expirationDate(request.getExpirationDate())
                .credentialUrl(request.getCredentialUrl())
                .imageUrl(request.getImageUrl())
                .build();
        return mapToResponse(certificateRepository.save(cert));
    }

    public List<CertificateResponse> getAll() {
        return certificateRepository.findAll().stream().map(this::mapToResponse).toList();
    }

    public void delete(Long id) { certificateRepository.deleteById(id); }

    private CertificateResponse mapToResponse(Certificate cert) {
        return CertificateResponse.builder()
                .id(cert.getId()).name(cert.getName()).organization(cert.getOrganization())
                .issueDate(cert.getIssueDate()).expirationDate(cert.getExpirationDate())
                .credentialUrl(cert.getCredentialUrl()).imageUrl(cert.getImageUrl()).build();
    }
}
