package org.example.be_porfolio.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "project_media")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectMedia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 500)
    private String mediaUrl;

    private String publicId; // ID từ Cloudinary để sau này cần xóa file

    @Enumerated(EnumType.STRING)
    private MediaType mediaType; // IMAGE hoặc VIDEO

    private boolean isThumbnail = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project project;
}
