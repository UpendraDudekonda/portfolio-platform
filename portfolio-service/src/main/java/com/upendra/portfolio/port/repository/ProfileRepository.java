package com.upendra.portfolio.port.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.upendra.portfolio.port.entity.Profile;

public interface ProfileRepository extends JpaRepository<Profile, Long> {

	Optional<Profile> findByUserUuid(UUID userUuid);

	boolean existsByUserUuid(UUID userUuid);
	
	Optional<Profile> findFirstByOrderByIdAsc();
}
