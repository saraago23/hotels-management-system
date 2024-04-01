package com.academy.project.hotelsmanagementsystem.repository;

import com.academy.project.hotelsmanagementsystem.entity.RoomTypeEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoomTypeRepository extends JpaRepository<RoomTypeEntity,Long> {
    Optional<RoomTypeEntity> findByIdAndDeletedFalse(Long id);
    @Query("SELECT rt FROM RoomTypeEntity rt WHERE rt.deleted=false ")
    Page<RoomTypeEntity> findAllNonDeleted(Pageable pageable);
    @Query("SELECT rt FROM RoomTypeEntity rt WHERE rt.deleted=true ")

    Page<RoomTypeEntity>findAllDeleted(Pageable pageable);
}
