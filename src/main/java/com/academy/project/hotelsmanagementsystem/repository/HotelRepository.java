package com.academy.project.hotelsmanagementsystem.repository;

import com.academy.project.hotelsmanagementsystem.entity.HotelEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HotelRepository extends JpaRepository<HotelEntity, Long> {

    Optional<HotelEntity> findByIdAndDeletedFalse(Long id);
    @Query("SELECT h FROM HotelEntity h WHERE h.deleted=false ")
    Page<HotelEntity> findAllNonDeleted(Pageable pageable);
}
