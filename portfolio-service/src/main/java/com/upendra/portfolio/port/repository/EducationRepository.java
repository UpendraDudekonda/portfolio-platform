package com.upendra.portfolio.port.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.upendra.portfolio.port.entity.Education;

@Repository
public interface EducationRepository extends JpaRepository<Education, Long> {

    List<Education> findByUserUuidOrderByDisplayOrderAsc(UUID userUuid);

    Optional<Education> findByIdAndUserUuid(Long id, UUID userUuid);

    boolean existsByUserUuidAndInstitutionNameAndDegree(
            UUID userUuid,
            String institutionName,
            String degree);
}
