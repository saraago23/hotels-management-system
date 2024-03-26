package com.academy.project.hotelsmanagementsystem.service;

import com.academy.project.hotelsmanagementsystem.dto.PageDTO;
import com.academy.project.hotelsmanagementsystem.dto.RoomBookedDTO;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface RoomBookedService {
    PageDTO<RoomBookedDTO> findAll(Pageable pageable);

    RoomBookedDTO addRoomBooked(@Valid RoomBookedDTO roomBookedDTO);
    RoomBookedDTO findRoomBookedById(Long id);
    RoomBookedDTO updateRoomBooked(Long id,@Valid RoomBookedDTO updatedRoomBooked);
    void deleteRoomBooked(Long id);
}
