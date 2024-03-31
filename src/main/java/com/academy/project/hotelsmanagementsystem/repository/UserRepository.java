package com.academy.project.hotelsmanagementsystem.repository;

import com.academy.project.hotelsmanagementsystem.entity.BookingEntity;
import com.academy.project.hotelsmanagementsystem.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,Long> {

    @Query("SELECT u FROM UserEntity u WHERE u.deleted=false ")
    Page<UserEntity> findAllNonDeleted(Pageable pageable);
    @Query("SELECT u FROM UserEntity u WHERE u.deleted=true ")

    Page<UserEntity>findAllDeleted(Pageable pageable);
    Optional<UserEntity> findByUsername(String username);

    List<UserEntity> findByRoleId(Long id);
}
