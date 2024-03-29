package com.academy.project.hotelsmanagementsystem.service.impl;

import com.academy.project.hotelsmanagementsystem.dto.PageDTO;
import com.academy.project.hotelsmanagementsystem.dto.RoomBookedDTO;
import com.academy.project.hotelsmanagementsystem.dto.RoomDTO;
import com.academy.project.hotelsmanagementsystem.entity.BookingEntity;
import com.academy.project.hotelsmanagementsystem.entity.RoomBookedEntity;
import com.academy.project.hotelsmanagementsystem.entity.RoomEntity;
import com.academy.project.hotelsmanagementsystem.exceptions.GeneralException;
import com.academy.project.hotelsmanagementsystem.repository.BookingRepository;
import com.academy.project.hotelsmanagementsystem.repository.RoomBookedRepository;
import com.academy.project.hotelsmanagementsystem.repository.RoomRepository;
import com.academy.project.hotelsmanagementsystem.service.RoomService;
import com.academy.project.hotelsmanagementsystem.utils.PageUtils;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.academy.project.hotelsmanagementsystem.mapper.RoomMapper.*;

@Validated
@Service
public class RoomServiceImpl implements RoomService {
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private RoomBookedRepository roomBookedRepository;

    @Override
    public PageDTO<RoomDTO> findAll(Pageable pageable) {
        return PageUtils.toPageImpl(roomRepository.findAll(pageable), ROOM_MAPPER);
    }

    @Override
    public RoomDTO addRoom(@Valid RoomDTO roomDTO) {
        var entity = roomRepository.save(ROOM_MAPPER.toEntity(roomDTO));
        return ROOM_MAPPER.toDto(entity);
    }

    @Override
    public RoomDTO findRoomById(Long id) {
        RoomEntity roomEntity = roomRepository.findById(id).orElseThrow(() -> new GeneralException("Room with id: " + id + " was not found"));
        return ROOM_MAPPER.toDto(roomEntity);
    }

    @Override
    public RoomDTO updateRoom(Long id, @Valid RoomDTO updatedRoom) {
        if (roomRepository.existsById(id)) {
            updatedRoom.setId(id);
            return ROOM_MAPPER.toDto(roomRepository.save(ROOM_MAPPER.toEntity(updatedRoom)));
        }
        throw new GeneralException("Room with id: " + id + " was not found");
    }

    @Override
    public List<RoomDTO> getAvailableRooms(LocalDateTime checkIn, LocalDateTime checkOut) {
        List<RoomEntity> roomsBooked= new ArrayList<>();
        List<BookingEntity> bookings= bookingRepository.findAllByCheckInTimeAfterAndCheckOutTimeBefore(checkIn,checkOut);

        bookings.forEach(booking ->{
            List<RoomBookedEntity> roomBookedEntities=roomBookedRepository.findRoomBookedByBookingId(booking.getId());
            roomBookedEntities.forEach(roomBookedEntity -> {roomsBooked.add(roomBookedEntity.getRoom());});
        } );

        List<RoomEntity> allRoomsAvailable=roomRepository.findAll();
        for (RoomEntity roomBooked:roomsBooked) {
            allRoomsAvailable.remove(roomBooked);
        }

        return allRoomsAvailable.stream().map(ROOM_MAPPER::toDto).collect(Collectors.toList());
    }

    @Override
    public void deleteRoom(Long id) {

    }
}
