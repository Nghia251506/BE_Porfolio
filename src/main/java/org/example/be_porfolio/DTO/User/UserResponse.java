package org.example.be_porfolio.DTO.User;

import lombok.Builder;
import lombok.Data;
import org.example.be_porfolio.Entity.UserRole;

import java.time.LocalDateTime;

@Data
@Builder
public class UserResponse {
    private Long id;
    private String username;
    private String fullName;
    private String email;
    private UserRole role;
    private LocalDateTime createdAt;
}
