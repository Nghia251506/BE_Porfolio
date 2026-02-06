package org.example.be_porfolio.Entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "tech_stacks")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TechStack {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name; // VD: "Spring Boot"

    private String iconUrl; // URL logo công nghệ từ Cloudinary

    @Column(length = 50)
    private String proficiency; // Mức độ: "Advanced", "Intermediate", hoặc "90%"

    @Enumerated(EnumType.STRING)
    private TechCategory category; // Phân loại: BACKEND, FRONTEND, DATABASE, TOOLS

    @CreationTimestamp
    private LocalDateTime createdAt;

    // Quan hệ Many-to-Many với Project sẽ được định nghĩa bên Project Entity
    @ManyToMany(mappedBy = "techStacks")
    private Set<Project> projects;
}