package com.academy.project.hotelsmanagementsystem.service;

import com.academy.project.hotelsmanagementsystem.dto.PageDTO;
import com.academy.project.hotelsmanagementsystem.dto.RoomDTO;
import com.academy.project.hotelsmanagementsystem.entity.RoomEntity;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface RoomService {
    PageDTO<RoomDTO> findAll(Pageable pageable);

    RoomDTO addRoom(@Valid RoomDTO req);
    Optional<RoomEntity> findRoomById(Integer id);
    RoomDTO updateRoom(Integer id,@Valid RoomDTO req);
    void deleteRoom(Integer id);

}
