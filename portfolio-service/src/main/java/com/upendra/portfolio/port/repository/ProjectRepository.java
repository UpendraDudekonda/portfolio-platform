package com.upendra.portfolio.port.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.upendra.portfolio.port.entity.Project;
import com.upendra.portfolio.port.entity.ProjectImage;

public interface ProjectRepository extends JpaRepository<Project, Long> {

    List<Project> findByUserUuidOrderByDisplayOrderAsc(UUID userUuid);

    Optional<Project> findByIdAndUserUuid(Long id, UUID userUuid);

    boolean existsByUserUuidAndTitle(UUID userUuid,
                                     String title);
   
}
