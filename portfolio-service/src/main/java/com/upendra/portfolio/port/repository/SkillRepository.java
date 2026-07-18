package com.upendra.portfolio.port.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.upendra.portfolio.port.entity.Skill;

@Repository
public interface SkillRepository extends JpaRepository<Skill, Long> {

	 // Check duplicate skill for a user
    boolean existsByUserUuidAndSkillName(UUID userUuid, String skillName);

    // Get all skills ordered by display order
    List<Skill> findByUserUuidOrderByDisplayOrderAsc(UUID userUuid);

    // Get one skill belonging to a user
    Optional<Skill> findByIdAndUserUuid(Long id, UUID userUuid);
}
