package org.example.be_porfolio.DTO.TechStack;

import lombok.*;
import org.example.be_porfolio.Entity.TechCategory;

@Data
@Builder
public class TechStackResponse {
    private Long id;
    private String name;
    private String iconUrl;
    private String proficiency;
    private TechCategory category;
}
