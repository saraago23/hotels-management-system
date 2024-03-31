package com.academy.project.hotelsmanagementsystem.repository;

import com.academy.project.hotelsmanagementsystem.entity.RoomEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<RoomEntity, Long> {

    List<RoomEntity> getRoomEntitiesByHotel_Id(Long id);

    @Query("SELECT r FROM RoomEntity r WHERE r.deleted=false ")
    Page<RoomEntity> findAllNonDeleted(Pageable pageable);

    @Query("SELECT r.roomNr FROM RoomEntity r WHERE r.hotel.id = :hotelId")
    List<Integer> findRoomNumbersByHotelId(Long hotelId);
}
