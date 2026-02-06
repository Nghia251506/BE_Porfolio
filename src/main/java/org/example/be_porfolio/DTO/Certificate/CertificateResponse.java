package org.example.be_porfolio.DTO.Certificate;

import lombok.*;

@Data
@Builder
public class CertificateResponse {
    private Long id;
    private String name;
    private String organization;
    private String issueDate;
    private String expirationDate;
    private String credentialUrl;
    private String imageUrl;
}
