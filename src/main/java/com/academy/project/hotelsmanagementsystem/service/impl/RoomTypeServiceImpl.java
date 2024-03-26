package com.academy.project.hotelsmanagementsystem.service.impl;

import com.academy.project.hotelsmanagementsystem.dto.PageDTO;
import com.academy.project.hotelsmanagementsystem.dto.RoomTypeDTO;
import com.academy.project.hotelsmanagementsystem.entity.RoomTypeEntity;
import com.academy.project.hotelsmanagementsystem.exceptions.GeneralException;
import com.academy.project.hotelsmanagementsystem.repository.RoomTypeRepository;
import com.academy.project.hotelsmanagementsystem.service.RoomTypeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;


import static com.academy.project.hotelsmanagementsystem.utils.PageUtils.*;
import static com.academy.project.hotelsmanagementsystem.mapper.RoomTypeMapper.*;

@Service
@Validated
public class RoomTypeServiceImpl implements RoomTypeService {
    @Autowired
    private RoomTypeRepository roomTypeRepository;

    @Override
    public PageDTO<RoomTypeDTO> findAll(Pageable pageable) {
        return toPageImpl(roomTypeRepository.findAll(pageable), ROOM_TYPE_MAPPER);
    }

    @Override
    public RoomTypeDTO addRoomType(@Valid RoomTypeDTO roomTypeDTO) {
        var entity = roomTypeRepository.save(ROOM_TYPE_MAPPER.toEntity(roomTypeDTO));
        return ROOM_TYPE_MAPPER.toDto(entity);
    }

    @Override
    public RoomTypeDTO findRoomTypeById(Long id) {
        RoomTypeEntity roomTypeEntity=roomTypeRepository.findById(id).orElseThrow(()-> new GeneralException("Room Type with id: " + id + " was not found"));
        return ROOM_TYPE_MAPPER.toDto(roomTypeEntity);
    }

    @Override
    public RoomTypeDTO updateRoomType(Long id, @Valid RoomTypeDTO updatedRoomType) {
        if (roomTypeRepository.existsById(id)) {
            updatedRoomType.setId(id);
            return ROOM_TYPE_MAPPER.toDto(roomTypeRepository.save(ROOM_TYPE_MAPPER.toEntity(updatedRoomType)));
        }
        throw new GeneralException("Room Type with id: " + id + " was not found");
    }

    @Override
    public void deleteRoomType(Long id) {

    }
}
