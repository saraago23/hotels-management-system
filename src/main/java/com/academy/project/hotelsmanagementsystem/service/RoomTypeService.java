package com.academy.project.hotelsmanagementsystem.service;

import com.academy.project.hotelsmanagementsystem.dto.PageDTO;
import com.academy.project.hotelsmanagementsystem.dto.RoomTypeDTO;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface RoomTypeService {
    PageDTO<RoomTypeDTO> findAll(Pageable pageable);

    RoomTypeDTO addRoomType(@Valid RoomTypeDTO req);
    Optional<RoomTypeDTO> findRoomTypeById(Integer id);
    RoomTypeDTO updateRoomType(Integer id,@Valid RoomTypeDTO req);
    void deleteRoomType(Integer id);

}
