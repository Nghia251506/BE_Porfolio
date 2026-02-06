package org.example.be_porfolio.DTO.Contact;

import lombok.Data;

@Data
public class ContactRequest {
    private String fullName;
    private String email;
    private String subject;
    private String message;
}
