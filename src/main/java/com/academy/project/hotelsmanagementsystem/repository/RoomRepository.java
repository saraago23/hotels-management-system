package com.academy.project.hotelsmanagementsystem.repository;

import com.academy.project.hotelsmanagementsystem.entity.RoomEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<RoomEntity, Long> {

    Optional<RoomEntity> findByIdAndDeletedFalse(Long id);
    List<RoomEntity> getRoomEntitiesByHotel_IdAndDeletedFalse(Long id);

    @Query("SELECT r FROM RoomEntity r WHERE r.deleted=false ")
    Page<RoomEntity> findAllNonDeleted(Pageable pageable);

    @Query("SELECT r FROM RoomEntity r WHERE r.deleted=false ")
    List<RoomEntity> findAllNonDeleted();

    @Query("SELECT r.roomNr FROM RoomEntity r WHERE r.hotel.id = :hotelId")
    List<Integer> findRoomNumbersByHotelIdAndDeletedFalse(Long hotelId);
}
