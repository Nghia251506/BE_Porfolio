package org.example.be_porfolio.Entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "certificates")
@Getter
@Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Certificate {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name; // Tên chứng chỉ (VD: AWS Certified Developer)

    private String organization; // Tổ chức cấp (VD: Amazon, Oracle)

    private String issueDate; // Ngày cấp (Dạng String hoặc LocalDate)

    private String expirationDate; // Ngày hết hạn (nếu có)

    private String credentialUrl; // Link kiểm tra chứng chỉ online

    private String imageUrl; // Ảnh chụp chứng chỉ (Upload lên Cloudinary)

    @CreationTimestamp
    private LocalDateTime createdAt;
}
