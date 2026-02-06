package org.example.be_porfolio.DTO.Contact;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
public class ContactResponse {
    private Long id;
    private String fullName;
    private String email;
    private String subject;
    private String message;
    private boolean isRead;
    private LocalDateTime createdAt;
}
