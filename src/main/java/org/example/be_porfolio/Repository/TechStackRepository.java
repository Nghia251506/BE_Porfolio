package org.example.be_porfolio.Repository;

import org.example.be_porfolio.Entity.TechStack;
import org.example.be_porfolio.Entity.TechCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TechStackRepository extends JpaRepository<TechStack, Long> {
    Optional<TechStack> findByName(String name);
    List<TechStack> findByCategory(TechCategory category);

    // Tìm tất cả tech stack có ID nằm trong danh sách gửi từ FE
    List<TechStack> findAllByIdIn(List<Long> ids);
}