package com.academy.project.hotelsmanagementsystem.repository;

import com.academy.project.hotelsmanagementsystem.dto.RoomDTO;
import com.academy.project.hotelsmanagementsystem.entity.RoomBookedEntity;
import com.academy.project.hotelsmanagementsystem.entity.RoomEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomBookedRepository extends JpaRepository<RoomBookedEntity,Long> {
    List<RoomBookedEntity> findRoomBookedByBookingId(Long bookingId);
    List<RoomBookedEntity> findRoomBookedByRoom(RoomEntity room);
    @Query("SELECT rb FROM RoomBookedEntity rb JOIN rb.room r JOIN r.hotel h WHERE h.id = :hotelId")
    List<RoomBookedEntity> findRoomBookedEntitiesByHotelId(Long hotelId );

    @Query("SELECT rb FROM RoomBookedEntity rb WHERE rb.deleted=false ")
    Page<RoomBookedEntity> findAllNonDeleted(Pageable pageable);
    @Query("SELECT rb FROM RoomBookedEntity rb WHERE rb.deleted=true ")
    Page<RoomBookedEntity> findAllDeleted(Pageable pageable);

    @Query("SELECT r.roomNr FROM RoomEntity r WHERE r.hotel.id = :hotelId")
    List<Integer> findRoomNumbersByHotelId(Long hotelId);
}
