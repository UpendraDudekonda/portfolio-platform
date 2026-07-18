package com.upendra.portfolio.port.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.upendra.portfolio.port.entity.Certification;

public interface CertificationRepository extends JpaRepository<Certification,Long>{

    List<Certification> findByUserUuidOrderByDisplayOrderAsc(UUID userUuid);

    Optional<Certification> findByIdAndUserUuid(Long id,UUID userUuid);

    boolean existsByUserUuidAndName(UUID userUuid,String name);

}
