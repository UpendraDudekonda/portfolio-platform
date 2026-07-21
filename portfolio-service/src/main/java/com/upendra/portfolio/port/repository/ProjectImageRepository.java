package com.upendra.portfolio.port.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.upendra.portfolio.port.entity.ProjectImage;

public interface ProjectImageRepository
        extends JpaRepository<ProjectImage, Long> {

    List<ProjectImage> findByProjectIdOrderByDisplayOrderAsc(
            Long projectId);

    Optional<ProjectImage> findByIdAndProjectId(
            Long imageId,
            Long projectId);

    void deleteByProjectId(
            Long projectId);


    long countByProjectId(Long projectId);

}