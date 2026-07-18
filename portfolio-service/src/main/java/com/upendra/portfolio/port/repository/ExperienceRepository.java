package com.upendra.portfolio.port.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.upendra.portfolio.port.entity.Experience;

@Repository
public interface ExperienceRepository
        extends JpaRepository<Experience, Long> {

    List<Experience> findByUserUuidOrderByDisplayOrderAsc(UUID userUuid);

    Optional<Experience> findByIdAndUserUuid(
            Long id,
            UUID userUuid);
}