package com.upendra.portfolio.port.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.upendra.portfolio.port.entity.Profile;
import com.upendra.portfolio.port.entity.SocialLink;
import com.upendra.portfolio.port.enums.SocialPlatform;

public interface SocialLinkRepository extends JpaRepository<SocialLink, Long> {

	List<SocialLink> findByUserUuidOrderByDisplayOrderAsc(UUID userUuid);

		Optional<SocialLink> findByIdAndUserUuid(Long id,UUID userUuid);

		boolean existsByUserUuidAndPlatform(UUID userUuid,SocialPlatform platform);

		//Optional<Profile> findByUserUuidOrderByDisplayOrderAsc(UUID userUuid);

}
