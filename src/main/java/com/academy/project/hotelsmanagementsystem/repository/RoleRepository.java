package com.academy.project.hotelsmanagementsystem.repository;

import com.academy.project.hotelsmanagementsystem.entity.RoleEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    Optional<RoleEntity> findByIdAndDeletedFalse(Long id);

    @Query("SELECT r FROM RoleEntity r WHERE r.deleted=false ")
    Page<RoleEntity> findAllNonDeleted(Pageable pageable);

    @Query("SELECT r FROM RoleEntity r WHERE r.deleted=true ")
    Page<RoleEntity> findAllDeleted(Pageable pageable);

    @Query("SELECT r FROM RoleEntity r WHERE r.title=:role")
    RoleEntity findByRoleTitle(String role);

}
