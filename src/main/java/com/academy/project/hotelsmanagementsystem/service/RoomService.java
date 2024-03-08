package com.academy.project.hotelsmanagementsystem.service;

import com.academy.project.hotelsmanagementsystem.dto.PageDTO;
import com.academy.project.hotelsmanagementsystem.dto.RoomDTO;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface RoomService {
    PageDTO<RoomDTO> findAll(Pageable pageable);

    RoomDTO addRoom(@Valid RoomDTO req);
    Optional<RoomDTO> findRoomById(Long id);
    RoomDTO updateRoom(Long id,@Valid RoomDTO req);
    void deleteRoom(Long id);

}
