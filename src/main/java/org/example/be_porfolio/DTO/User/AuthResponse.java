package org.example.be_porfolio.DTO.User;

import lombok.*;

@Data
@Builder
public class AuthResponse {
    private String accessToken;
    private String tokenType = "Bearer";
    private UserResponse user;
}
