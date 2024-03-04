package com.academy.project.hotelsmanagementsystem.service.impl;

import com.academy.project.hotelsmanagementsystem.dto.PageDTO;
import com.academy.project.hotelsmanagementsystem.dto.RoomBookedDTO;
import com.academy.project.hotelsmanagementsystem.repository.RoomBookedRepository;
import com.academy.project.hotelsmanagementsystem.service.RoomBookedService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;

import static com.academy.project.hotelsmanagementsystem.utils.PageUtils.*;
import static com.academy.project.hotelsmanagementsystem.mapper.RoomBookedMapper.*;

@Service
@Validated
public class RoomBookedServiceImpl implements RoomBookedService {
    @Autowired
    private RoomBookedRepository roomBookedRepository;

    @Override
    public PageDTO<RoomBookedDTO> findAll(Pageable pageable) {
        return toPageImpl(roomBookedRepository.findAll(pageable), ROOM_BOOKED_MAPPER);
    }

    @Override
    public RoomBookedDTO addRoomBooked(@Valid RoomBookedDTO req) {
        var entity = roomBookedRepository.save(ROOM_BOOKED_MAPPER.toEntity(req));
        return ROOM_BOOKED_MAPPER.toDto(entity);
    }

    @Override
    public Optional<RoomBookedDTO> findRoomBookedById(Integer id) {
        return roomBookedRepository.findById(id)
                .map(ROOM_BOOKED_MAPPER::toDto);
    }

    @Override
    public RoomBookedDTO updateRoomBooked(Integer id, @Valid RoomBookedDTO req) {
        req.setId(id);
        return roomBookedRepository.findById(id)
                .map(o -> ROOM_BOOKED_MAPPER.toEntity(req))
                .map(roomBookedRepository::save)
                .map(ROOM_BOOKED_MAPPER::toDto)
                .orElse(null);

    }

    @Override
    public void deleteRoomBooked(Integer id) {

    }
}
