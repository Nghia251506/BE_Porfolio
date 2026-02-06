package org.example.be_porfolio.DTO.Certificate;

import lombok.Data;

@Data
public class CertificateRequest {
    private String name;
    private String organization;
    private String issueDate;
    private String expirationDate;
    private String credentialUrl;
    private String imageUrl;
}
