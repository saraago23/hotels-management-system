package com.academy.project.hotelsmanagementsystem.service.impl;

import com.academy.project.hotelsmanagementsystem.dto.PageDTO;
import com.academy.project.hotelsmanagementsystem.dto.RoomTypeDTO;
import com.academy.project.hotelsmanagementsystem.repository.RoomTypeRepository;
import com.academy.project.hotelsmanagementsystem.service.RoomTypeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;

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
    public RoomTypeDTO addRoomType(@Valid RoomTypeDTO req) {
        var entity = roomTypeRepository.save(ROOM_TYPE_MAPPER.toEntity(req));
        return ROOM_TYPE_MAPPER.toDto(entity);
    }

    @Override
    public Optional<RoomTypeDTO> findRoomTypeById(Long id) {
        return roomTypeRepository.findById(id)
                .map(ROOM_TYPE_MAPPER::toDto);
    }

    @Override
    public RoomTypeDTO updateRoomType(Long id, @Valid RoomTypeDTO req) {
        req.setId(id);
        return roomTypeRepository.findById(id)
                .map(o->ROOM_TYPE_MAPPER.toEntity(req))
                .map(roomTypeRepository::save)
                .map(ROOM_TYPE_MAPPER::toDto).orElse(null);
    }

    @Override
    public void deleteRoomType(Long id) {

    }
}
