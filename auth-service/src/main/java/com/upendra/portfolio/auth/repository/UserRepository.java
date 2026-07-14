package com.upendra.portfolio.auth.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.upendra.portfolio.auth.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{

	Optional<User> findByEmail(String email);
	Optional<User> findByUuid(UUID uuid);

    boolean existsByEmail(String email);
}
