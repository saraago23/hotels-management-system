package com.academy.project.hotelsmanagementsystem.service.impl;

import com.academy.project.hotelsmanagementsystem.dto.PageDTO;
import com.academy.project.hotelsmanagementsystem.dto.RoomTypeDTO;
import com.academy.project.hotelsmanagementsystem.entity.RoomTypeEntity;
import com.academy.project.hotelsmanagementsystem.exceptions.GeneralException;
import com.academy.project.hotelsmanagementsystem.repository.RoomTypeRepository;
import com.academy.project.hotelsmanagementsystem.service.RoomTypeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;


import static com.academy.project.hotelsmanagementsystem.utils.PageUtils.*;
import static com.academy.project.hotelsmanagementsystem.mapper.RoomTypeMapper.*;

@Service
@Validated
@RequiredArgsConstructor
public class RoomTypeServiceImpl implements RoomTypeService {

    private final RoomTypeRepository roomTypeRepository;

    @Override
    public PageDTO<RoomTypeDTO> findAll(Pageable pageable) {
        return toPageImpl(roomTypeRepository.findAllNonDeleted(pageable), ROOM_TYPE_MAPPER);
    }

    //admin
    @Override
    public RoomTypeDTO addRoomType(@Valid RoomTypeDTO roomTypeDTO) {

        RoomTypeEntity roomTypeEntity = RoomTypeEntity.builder()
                .type(roomTypeDTO.getType())
                .roomDesc(roomTypeDTO.getRoomDesc())
                .numGuest(roomTypeDTO.getNumGuest())
                .price(roomTypeDTO.getPrice())
                .deleted(false)
                .build();

        return ROOM_TYPE_MAPPER.toDto(roomTypeRepository.save(roomTypeEntity));
    }

    @Override
    public RoomTypeDTO findRoomTypeById(Long id) {
        RoomTypeEntity roomTypeEntity = roomTypeRepository.findByIdAndDeletedFalse(id).orElseThrow(() -> new GeneralException("Room Type with id: " + id + " was not found"));
        return ROOM_TYPE_MAPPER.toDto(roomTypeEntity);
    }

    @Override
    public RoomTypeDTO updateRoomType(Long id, @Valid RoomTypeDTO updatedRoomType) {

        RoomTypeEntity roomTypeToBeUpdated = roomTypeRepository.findByIdAndDeletedFalse(id).orElseThrow(() -> new GeneralException("Room Type with id: " + id + " was not found"));

        RoomTypeDTO roomTypeDTO = ROOM_TYPE_MAPPER.toDto(roomTypeToBeUpdated);

        roomTypeDTO.setType(updatedRoomType.getType());
        roomTypeDTO.setRoomDesc(updatedRoomType.getRoomDesc());
        roomTypeDTO.setNumGuest(updatedRoomType.getNumGuest());
        roomTypeDTO.setPrice(updatedRoomType.getPrice());

        return ROOM_TYPE_MAPPER.toDto(roomTypeRepository.save(ROOM_TYPE_MAPPER.toEntity(roomTypeDTO)));

    }

    @Override
    public void deleteRoomType(Long id) {
        RoomTypeEntity roomTypeToBeDeleted = roomTypeRepository.findByIdAndDeletedFalse(id).orElseThrow(() -> new GeneralException("Room Type with id: " + id + " was not found"));
        RoomTypeDTO roomTypeDTO = ROOM_TYPE_MAPPER.toDto(roomTypeToBeDeleted);

        roomTypeDTO.setDeleted(true);
        roomTypeRepository.save(ROOM_TYPE_MAPPER.toEntity(roomTypeDTO));

    }
}

