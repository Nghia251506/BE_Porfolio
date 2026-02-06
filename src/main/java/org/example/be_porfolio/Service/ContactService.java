package org.example.be_porfolio.Service;

import lombok.RequiredArgsConstructor;
import org.example.be_porfolio.DTO.Contact.ContactRequest;
import org.example.be_porfolio.DTO.Contact.ContactResponse;
import org.example.be_porfolio.Entity.Contact;
import org.example.be_porfolio.Repository.ContactRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContactService {
    private final ContactRepository contactRepository;

    public void saveContact(ContactRequest request) {
        Contact contact = Contact.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .subject(request.getSubject())
                .message(request.getMessage())
                .isRead(false)
                .build();
        contactRepository.save(contact);
        // Sau này ông có thể thêm logic gửi Email thông báo tại đây
    }

    public List<ContactResponse> getAll() {
        return contactRepository.findAll().stream()
                .map(c -> ContactResponse.builder()
                        .id(c.getId()).fullName(c.getFullName()).email(c.getEmail())
                        .subject(c.getSubject()).message(c.getMessage())
                        .isRead(c.isRead()).createdAt(c.getCreatedAt()).build())
                .toList();
    }
}
