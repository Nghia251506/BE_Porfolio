package org.example.be_porfolio.DTO.Project;

import lombok.*;
import org.example.be_porfolio.Entity.MediaType;


@Data
@Builder
public class ProjectMediaResponse {
    private Long id;
    private String mediaUrl;
    private MediaType mediaType;
    private boolean isThumbnail;
}
