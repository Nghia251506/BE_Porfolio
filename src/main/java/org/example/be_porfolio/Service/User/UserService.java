package org.example.be_porfolio.Service.User;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.be_porfolio.DTO.User.AuthResponse;
import org.example.be_porfolio.DTO.User.LoginRequest;
import org.example.be_porfolio.DTO.User.UserRequest;
import org.example.be_porfolio.DTO.User.UserResponse;
import org.example.be_porfolio.Entity.User;
import org.example.be_porfolio.Entity.UserRole;
import org.example.be_porfolio.Repository.UserRepository;
import org.example.be_porfolio.Security.JwtTokenProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    public UserResponse register(UserRequest request) {
        if(userRepository.existsByUsername(request.getUsername())) throw new RuntimeException("Username đã tồn tại");

        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .fullName(request.getFullName())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(UserRole.ADMIN)
                .build();

        userRepository.save(user);
        return mapToResponse(user);
    }

    private UserResponse mapToResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole())
                .createdAt(user.getCreatedAt())
                .build();
    }

    @Transactional
    public AuthResponse login(LoginRequest dto, HttpServletResponse response) {
        // 1. Xác thực người dùng
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        User user = userRepository.findByUsername(dto.getUsername())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy user"));

        // 2. Tạo JWT Token
        String token = jwtTokenProvider.generateToken(user);

        // 3. TẠO VÀ CẤU HÌNH COOKIE
        Cookie cookie = new Cookie("access_token", token);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(7 * 24 * 60 * 60);
        cookie.setAttribute("SameSite", "None");
        cookie.setAttribute("Partitioned", "");
        response.addCookie(cookie);

        return AuthResponse.builder()
                .accessToken(token)
                .user(UserResponse.builder()
                        .id(user.getId())
                        .username(user.getUsername())
                        .email(user.getEmail())
                        .fullName(user.getFullName())
                        .role(user.getRole())
                        .build())
                .build();
    }

    public UserResponse getMe() {
        // 1. Lấy thông tin user hiện tại từ SecurityContext
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated() ||
                authentication.getPrincipal().equals("anonymousUser")) {
            throw new RuntimeException("Bạn chưa đăng nhập!");
        }

        // 2. Lấy username từ principal (tùy vào cách ông cài đặt CustomUserDetails)
        String username = authentication.getName();

        // 3. Tìm trong DB và trả về
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User không tồn tại"));

        return mapToResponse(user);
    }

    public void logout(HttpServletResponse response) {
        // 1. Xóa SecurityContext trên Server
        SecurityContextHolder.clearContext();

        // 2. Tạo cookie ghi đè để xóa
        Cookie cookie = new Cookie("access_token", null);

        // Các thuộc tính phải khớp với lúc Login để trình duyệt nhận diện đúng
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");

        // Set thời gian sống bằng 0 để xóa ngay lập tức
        cookie.setMaxAge(0);

        // Thêm các thuộc tính nâng cao nếu bạn đã dùng lúc Login
        cookie.setAttribute("SameSite", "None");
        cookie.setAttribute("Partitioned", "");

        // Gửi cookie về client để thực hiện xóa
        response.addCookie(cookie);
    }
}
