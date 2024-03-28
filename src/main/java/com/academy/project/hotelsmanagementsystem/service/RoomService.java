package com.academy.project.hotelsmanagementsystem.service;

import com.academy.project.hotelsmanagementsystem.dto.PageDTO;
import com.academy.project.hotelsmanagementsystem.dto.RoomDTO;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface RoomService {
    PageDTO<RoomDTO> findAll(Pageable pageable);

    RoomDTO addRoom(@Valid RoomDTO roomDTO);
    RoomDTO findRoomById(Long id);
    RoomDTO updateRoom(Long id,@Valid RoomDTO updatedRoom);
    List<RoomDTO> getAvailableRooms(LocalDateTime checkIn,LocalDateTime checkOut);
    void deleteRoom(Long id);

}
