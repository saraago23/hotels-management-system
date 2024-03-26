package com.academy.project.hotelsmanagementsystem.repository;

import com.academy.project.hotelsmanagementsystem.dto.RoomDTO;
import com.academy.project.hotelsmanagementsystem.entity.RoomBookedEntity;
import com.academy.project.hotelsmanagementsystem.entity.RoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomBookedRepository extends JpaRepository<RoomBookedEntity,Long> {
    List<RoomBookedEntity> findRoomBookedByBookingId(Long bookingId);
    List<RoomBookedEntity> findRoomBookedByRoom(RoomEntity room);
    @Query("SELECT rb FROM RoomBookedEntity rb JOIN rb.room r WHERE r.roomNr = :roomNr")
    List<RoomBookedEntity> findRoomBookedByRoomNr(Integer roomNr);
}
