package com.academy.project.hotelsmanagementsystem.repository;

import com.academy.project.hotelsmanagementsystem.entity.BookingEntity;
import com.academy.project.hotelsmanagementsystem.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<BookingEntity,Long> {

    @Query("SELECT b FROM BookingEntity b WHERE b.deleted=false ")
    Page<BookingEntity>findAllNonDeleted(Pageable pageable);
    @Query("SELECT b FROM BookingEntity b WHERE b.deleted=true ")

    Page<BookingEntity>findAllDeleted(Pageable pageable);

    List<BookingEntity> findBookingsByUser(UserEntity userEntity);

    List<BookingEntity> findAllByCheckInTimeAfterAndCheckOutTimeBefore(LocalDateTime checkInTime, LocalDateTime checkOutTime);
}
