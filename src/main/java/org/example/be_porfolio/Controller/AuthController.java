package org.example.be_porfolio.Controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.be_porfolio.DTO.User.AuthResponse;
import org.example.be_porfolio.DTO.User.LoginRequest;
import org.example.be_porfolio.DTO.User.UserRequest;
import org.example.be_porfolio.DTO.User.UserResponse;
import org.example.be_porfolio.Service.User.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Auth", description = "Bộ API xác thực và quản lý tài khoản")
public class AuthController {

    private final UserService userService;

    @PostMapping("/register")
    @Operation(summary = "Đăng ký Admin", description = "Chỉ dùng để tạo tài khoản Admin ban đầu")
    public ResponseEntity<UserResponse> register(@Valid @RequestBody UserRequest request) {
        return ResponseEntity.ok(userService.register(request));
    }

    @PostMapping("/login")
    @Operation(summary = "Đăng nhập", description = "Xác thực tài khoản và trả về Token qua Cookie/Body")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest loginRequest, HttpServletResponse response) {
        return ResponseEntity.ok(userService.login(loginRequest, response));
    }

    @GetMapping("/me")
    @Operation(summary = "Lấy thông tin hiện tại", description = "Dùng để kiểm tra trạng thái đăng nhập của Admin")
    public ResponseEntity<UserResponse> getMe() {
        return ResponseEntity.ok(userService.getMe());
    }

    @Operation(summary = "Đăng xuất", description = "Xóa Cookie access_token")
    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletResponse response) {
        userService.logout(response);
        return ResponseEntity.ok("Đăng xuất thành công");
    }
}