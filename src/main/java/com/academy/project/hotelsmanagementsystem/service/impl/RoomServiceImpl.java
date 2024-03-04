package com.academy.project.hotelsmanagementsystem.service.impl;

import com.academy.project.hotelsmanagementsystem.dto.PageDTO;
import com.academy.project.hotelsmanagementsystem.dto.RoomDTO;
import com.academy.project.hotelsmanagementsystem.repository.RoomRepository;
import com.academy.project.hotelsmanagementsystem.service.RoomService;
import com.academy.project.hotelsmanagementsystem.utils.PageUtils;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;

import static com.academy.project.hotelsmanagementsystem.mapper.RoomMapper.*;
@Validated
@Service
public class RoomServiceImpl implements RoomService {
    @Autowired
    private RoomRepository roomRepository;

    @Override
    public PageDTO<RoomDTO> findAll(Pageable pageable) {
        return PageUtils.toPageImpl(roomRepository.findAll(pageable), ROOM_MAPPER);
    }

    @Override
    public RoomDTO addRoom(@Valid RoomDTO req) {
        var entity = roomRepository.save(ROOM_MAPPER.toEntity(req));
        return ROOM_MAPPER.toDto(entity);
    }

    @Override
    public Optional<RoomDTO> findRoomById(Integer id) {
        return roomRepository.findById(id)
                .map(ROOM_MAPPER::toDto);
    }

    @Override
    public RoomDTO updateRoom(Integer id, @Valid RoomDTO req) {
        req.setId(id);
        return findRoomById(id).map(o -> ROOM_MAPPER.toEntity(req))
                .map(roomRepository::save)
                .map(ROOM_MAPPER::toDto).orElse(null);
    }

    @Override
    public void deleteRoom(Integer id) {

    }
}
