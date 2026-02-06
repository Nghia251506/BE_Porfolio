package org.example.be_porfolio.DTO.TechStack;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.example.be_porfolio.Entity.TechCategory;

@Data
public class TechStackRequest {
    @NotBlank(message = "Tên công nghệ không được để trống")
    private String name;
    private String iconUrl;
    private String proficiency;
    private TechCategory category;
}
