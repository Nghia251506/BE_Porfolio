package org.example.be_porfolio.Service.TechStack;

import lombok.RequiredArgsConstructor;
import org.example.be_porfolio.DTO.TechStack.TechStackRequest;
import org.example.be_porfolio.DTO.TechStack.TechStackResponse;
import org.example.be_porfolio.Entity.TechStack;
import org.example.be_porfolio.Repository.TechStackRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TechStackService {
    private final TechStackRepository techStackRepository;

    public TechStackResponse create(TechStackRequest request) {
        if(techStackRepository.findByName(request.getName()).isPresent())
            throw new RuntimeException("Công nghệ này đã tồn tại!");

        TechStack techStack = TechStack.builder()
                .name(request.getName())
                .iconUrl(request.getIconUrl())
                .proficiency(request.getProficiency())
                .category(request.getCategory())
                .build();
        return mapToResponse(techStackRepository.save(techStack));
    }

    public List<TechStackResponse> getAll() {
        return techStackRepository.findAll().stream()
                .map(this::mapToResponse).collect(Collectors.toList());
    }

    public void delete(Long id) {
        techStackRepository.deleteById(id);
    }

    private TechStackResponse mapToResponse(TechStack techStack) {
        return TechStackResponse.builder()
                .id(techStack.getId())
                .name(techStack.getName())
                .iconUrl(techStack.getIconUrl())
                .proficiency(techStack.getProficiency())
                .category(techStack.getCategory())
                .build();
    }
}