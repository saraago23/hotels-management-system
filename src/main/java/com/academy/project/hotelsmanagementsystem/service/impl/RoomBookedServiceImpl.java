package com.academy.project.hotelsmanagementsystem.service.impl;

import com.academy.project.hotelsmanagementsystem.dto.PageDTO;
import com.academy.project.hotelsmanagementsystem.dto.RoomBookedDTO;
import com.academy.project.hotelsmanagementsystem.entity.BookingEntity;
import com.academy.project.hotelsmanagementsystem.entity.RoomBookedEntity;
import com.academy.project.hotelsmanagementsystem.entity.RoomEntity;
import com.academy.project.hotelsmanagementsystem.exceptions.GeneralException;
import com.academy.project.hotelsmanagementsystem.repository.BookingRepository;
import com.academy.project.hotelsmanagementsystem.repository.RoomBookedRepository;
import com.academy.project.hotelsmanagementsystem.service.RoomBookedService;
import com.academy.project.hotelsmanagementsystem.utils.UserUtils;
import jakarta.validation.Valid;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;


import java.util.ArrayList;
import java.util.List;

import static com.academy.project.hotelsmanagementsystem.utils.PageUtils.*;
import static com.academy.project.hotelsmanagementsystem.mapper.RoomBookedMapper.*;

@Service
@Validated
public class RoomBookedServiceImpl implements RoomBookedService {
    @Autowired
    private RoomBookedRepository roomBookedRepository;
    @Autowired
    private BookingRepository bookingRepository;

    //admin
    @Override
    public PageDTO<RoomBookedDTO> findAll(Pageable pageable) {
        return toPageImpl(roomBookedRepository.findAllNonDeleted(pageable), ROOM_BOOKED_MAPPER);
    }

    @Override
    public PageDTO<RoomBookedDTO> findAllDeleted(Pageable pageable) {
        return toPageImpl(roomBookedRepository.findAllDeleted(pageable), ROOM_BOOKED_MAPPER);
    }

    @Override
    public RoomBookedDTO addRoomBooked(@Valid RoomBookedDTO roomBookedDTO) {
        throw new NotImplementedException("This service is not implemented. Please create a new booking if you need more rooms");
    }

    @Override
    public RoomBookedDTO findRoomBookedById(Long id) {
        RoomBookedEntity roomBookedEntity = roomBookedRepository.findById(id).orElseThrow(() -> new GeneralException("Room Booked with id: " + id + " was not found"));

        if (!(UserUtils.getLoggedUserRole().contains("ADMIN") || UserUtils.getLoggedUser().equals(roomBookedEntity.getBooking().getUser().getUsername()))) {
            throw new GeneralException("You have no access over this booked room");
        }

        if (roomBookedEntity.getDeleted()) {
            throw new GeneralException("No Booked Room with id: " + id + " was found");
        }

        return ROOM_BOOKED_MAPPER.toDto(roomBookedEntity);
    }

    @Override
    public RoomBookedDTO updateRoomBooked(Long id, @Valid RoomBookedDTO updatedRoomBooked) {
        RoomBookedEntity roomBookedToBeUpdated = roomBookedRepository.findById(id).orElseThrow(() -> new GeneralException("No booked room with id: " + id + " was found"));

        if (roomBookedToBeUpdated.getDeleted()) {
            throw new GeneralException("No booked room with id: " + id + " was found on the db");
        }

        if (!(UserUtils.getLoggedUserRole().contains("ADMIN") || UserUtils.getLoggedUser().equals(roomBookedToBeUpdated.getBooking().getUser().getUsername()))) {
            throw new GeneralException("You have no access to delete this booked room");
        }

        List<BookingEntity> existingBookings = bookingRepository.findAllByCheckInTimeAfterAndCheckOutTimeBefore(roomBookedToBeUpdated.getBooking().getCheckInTime(), roomBookedToBeUpdated.getBooking().getCheckOutTime());

        List<RoomEntity> availableRooms = new ArrayList<>();

        existingBookings.forEach(booking -> {
            List<RoomBookedEntity> roomBookedEntities = roomBookedRepository.findRoomBookedByBookingId(booking.getId());
            roomBookedEntities.forEach(roomBookedEntity -> {
                availableRooms.add(roomBookedEntity.getRoom());
            });
        });

        throw new NotImplementedException("This service is not implemented. Please create a new booking if you need more rooms");
    }

    @Override
    public void deleteRoomBooked(Long id) {
        RoomBookedEntity roomBookedToBeDeleted = roomBookedRepository.findById(id).orElseThrow(() -> new GeneralException("No Room Booked with id: " + id + " was found to be deleted"));

        if (!(UserUtils.getLoggedUserRole().contains("ADMIN") || UserUtils.getLoggedUser().equals(roomBookedToBeDeleted.getBooking().getUser().getUsername()))) {
            throw new GeneralException("You have no access to delete this booked room");
        }
        roomBookedToBeDeleted.setDeleted(true);

        roomBookedToBeDeleted.getBooking().setTotalPrice(roomBookedToBeDeleted.getBooking().getTotalPrice().subtract(roomBookedToBeDeleted.getPrice()));
        roomBookedToBeDeleted.getBooking().setTotalNumGuests(roomBookedToBeDeleted.getBooking().getTotalNumGuests() - roomBookedToBeDeleted.getRoom().getRoomType().getNumGuest());
        roomBookedRepository.save(roomBookedToBeDeleted);
    }
}
