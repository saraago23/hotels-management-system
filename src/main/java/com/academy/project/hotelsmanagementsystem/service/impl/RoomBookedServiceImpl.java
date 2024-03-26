package com.academy.project.hotelsmanagementsystem.service.impl;

import com.academy.project.hotelsmanagementsystem.dto.PageDTO;
import com.academy.project.hotelsmanagementsystem.dto.RoomBookedDTO;
import com.academy.project.hotelsmanagementsystem.entity.RoomBookedEntity;
import com.academy.project.hotelsmanagementsystem.exceptions.GeneralException;
import com.academy.project.hotelsmanagementsystem.repository.RoomBookedRepository;
import com.academy.project.hotelsmanagementsystem.service.RoomBookedService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;
import java.util.List;
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
    public RoomBookedDTO addRoomBooked(@Valid RoomBookedDTO roomBookedDTO) {
        /*List<RoomBookedEntity> bookings = roomBookedRepository.findRoomBookedByRoomNr(roomBookedDTO.getRoom().getRoomNr());
        boolean isBooked=false;
        for (RoomBookedEntity booking : bookings) {
            LocalDateTime existingCheckIn = booking.getBooking().getCheckInTime();
            LocalDateTime existingCheckOut = booking.getBooking().getCheckOutTime();

            if (existingCheckIn.isBefore(roomBookedDTO.getBooking().getCheckOutTime()) && existingCheckOut.isAfter(roomBookedDTO.getBooking().getCheckInTime())) {
                isBooked=true;
                break;
            }
        }
        if (isBooked) {
            throw new GeneralException("Room with id: " + roomBookedDTO.getRoom().getId() + " is already booked");
        }
        roomBookedDTO.getRoom().setBooked(true);
        var entity = roomBookedRepository.save(ROOM_BOOKED_MAPPER.toEntity(roomBookedDTO));
        return ROOM_BOOKED_MAPPER.toDto(entity);*/
        return null;
    }

    @Override
    public RoomBookedDTO findRoomBookedById(Long id) {

        RoomBookedEntity roomBookedEntity=roomBookedRepository.findById(id).orElseThrow(()->new GeneralException("Room Booked with id: " + id + " was not found"));
        return ROOM_BOOKED_MAPPER.toDto(roomBookedEntity);
    }

    @Override
    public RoomBookedDTO updateRoomBooked(Long id, @Valid RoomBookedDTO updatedRoomBooked) {
        if(roomBookedRepository.existsById(id)){
            updatedRoomBooked.setId(id);
            return ROOM_BOOKED_MAPPER.toDto(roomBookedRepository.save(ROOM_BOOKED_MAPPER.toEntity(updatedRoomBooked)));

        }
        throw new GeneralException("Room Booked with id: " + id + " was not found");

    }

    @Override
    public void deleteRoomBooked(Long id) {

    }
}
