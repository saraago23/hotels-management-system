package com.academy.project.hotelsmanagementsystem.service;

import com.academy.project.hotelsmanagementsystem.dto.PageDTO;
import com.academy.project.hotelsmanagementsystem.dto.RoomBookedDTO;
import com.academy.project.hotelsmanagementsystem.entity.RoomBookedEntity;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface RoomBookedService {
    PageDTO<RoomBookedDTO> findAll(Pageable pageable);

    RoomBookedDTO addRoomBooked(@Valid RoomBookedDTO req);
    Optional<RoomBookedEntity> findRoomBookedById(Integer id);
    RoomBookedDTO updateRoomBooked(Integer id,@Valid RoomBookedDTO req);
    void deleteRoomBooked(Integer id);
}
