package org.example.be_porfolio.Controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.be_porfolio.DTO.Contact.ContactRequest;
import org.example.be_porfolio.DTO.Contact.ContactResponse;
import org.example.be_porfolio.Service.ContactService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contacts")
@RequiredArgsConstructor
@Tag(name = "Contacts")
public class ContactController {
    private final ContactService contactService;

    @PostMapping // Public API cho khách gửi lời nhắn
    public ResponseEntity<String> sendContact(@RequestBody ContactRequest req) {
        contactService.saveContact(req);
        return ResponseEntity.ok("Cảm ơn ông, lời nhắn đã được gửi!");
    }

    @GetMapping // API cho Admin check tin nhắn
    public ResponseEntity<List<ContactResponse>> getAll() {
        return ResponseEntity.ok(contactService.getAll());
    }
}
