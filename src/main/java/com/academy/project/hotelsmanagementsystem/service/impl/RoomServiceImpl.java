package com.academy.project.hotelsmanagementsystem.service.impl;

import com.academy.project.hotelsmanagementsystem.dto.PageDTO;
import com.academy.project.hotelsmanagementsystem.dto.RoomDTO;
import com.academy.project.hotelsmanagementsystem.entity.*;
import com.academy.project.hotelsmanagementsystem.exceptions.GeneralException;
import com.academy.project.hotelsmanagementsystem.repository.*;
import com.academy.project.hotelsmanagementsystem.service.RoomService;
import com.academy.project.hotelsmanagementsystem.utils.PageUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.academy.project.hotelsmanagementsystem.mapper.RoomMapper.*;
import static com.academy.project.hotelsmanagementsystem.mapper.RoomTypeMapper.*;
import static com.academy.project.hotelsmanagementsystem.mapper.HotelMapper.*;

@Service
@Validated
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;
    private final BookingRepository bookingRepository;
    private final RoomBookedRepository roomBookedRepository;
    private final HotelRepository hotelRepository;
    private final RoomTypeRepository roomTypeRepository;

    @Override
    public PageDTO<RoomDTO> findAll(Pageable pageable) {
        return PageUtils.toPageImpl(roomRepository.findAllNonDeleted(pageable), ROOM_MAPPER);
    }

    @Override
    public RoomDTO addRoom(@Valid RoomDTO roomDTO) {

        HotelEntity hotelEntity = hotelRepository.findByIdAndDeletedFalse(roomDTO.getHotel().getId()).orElseThrow(() -> new GeneralException("This hotel does not exist on the system"));

        if (roomRepository.getRoomEntitiesByHotel_IdAndDeletedFalse(hotelEntity.getId()).size() >= hotelEntity.getRoomsNr()) {
            throw new GeneralException("You cannot add a new room for this hotel. Its capacity is: " + hotelEntity.getRoomsNr() + " rooms");
        }

        RoomTypeEntity roomTypeEntity = roomTypeRepository.findByIdAndDeletedFalse(roomDTO.getRoomType().getId())
                .orElseThrow(() -> new GeneralException("This room type does not exist on the system"));


        List<Integer> usedRoomNumbers = roomRepository.findRoomNumbersByHotelIdAndDeletedFalse(hotelEntity.getId());
        if (usedRoomNumbers.contains(roomDTO.getRoomNr())) {
            throw new GeneralException("Room " + roomDTO.getRoomNr() + " is already on the system");
        }

        int roomNumberStart = (int) (hotelEntity.getId() * 100);
        int roomNumberEnd = (int) ((hotelEntity.getId() + 1) * 100 - 1);

        if (roomDTO.getRoomNr() < roomNumberStart || roomDTO.getRoomNr() > roomNumberEnd) {
            throw new GeneralException("Room number must be between " + roomNumberStart + " and " + roomNumberEnd + " for this hotel");
        }

        RoomEntity newRoom = ROOM_MAPPER.toEntity(RoomDTO.builder()
                .hotel(HOTEL_MAPPER.toDto(hotelEntity))
                .roomType(ROOM_TYPE_MAPPER.toDto(roomTypeEntity))
                .roomNr(roomDTO.getRoomNr())
                .deleted(false)
                .build());

        return ROOM_MAPPER.toDto(roomRepository.save(newRoom));
    }

    @Override
    public RoomDTO findRoomById(Long id) {
        RoomEntity roomEntity = roomRepository.findByIdAndDeletedFalse(id).orElseThrow(() -> new GeneralException("Room with id: " + id + " was not found"));
        return ROOM_MAPPER.toDto(roomEntity);
    }

    @Override
    public RoomDTO updateRoom(Long id, @Valid RoomDTO updatedRoom) {

        RoomEntity roomToBeUpdated = roomRepository.findByIdAndDeletedFalse(id).orElseThrow(() -> new GeneralException("No room with id: " + id + " was found"));

        HotelEntity hotelEntity = hotelRepository.findByIdAndDeletedFalse(updatedRoom.getHotel().getId()).orElseThrow(() -> new GeneralException("This hotel does not exist in the system"));

        if (roomRepository.getRoomEntitiesByHotel_IdAndDeletedFalse(hotelEntity.getId()).size() >= hotelEntity.getRoomsNr()) {
            throw new GeneralException("You cannot add a new room for this hotel. Its capacity is: " + hotelEntity.getRoomsNr() + " rooms");
        }

        RoomTypeEntity roomTypeEntity = roomTypeRepository.findByIdAndDeletedFalse(updatedRoom.getRoomType().getId())
                .orElseThrow(() -> new GeneralException("This room type does not exist on the system"));


        List<Integer> usedRoomNumbers = roomRepository.findRoomNumbersByHotelIdAndDeletedFalse(hotelEntity.getId());
        if (usedRoomNumbers.contains(updatedRoom.getRoomNr())) {
            throw new GeneralException("Room " + updatedRoom.getRoomNr() + " is already on the system");
        }

        int roomNumberStart = (int) (hotelEntity.getId() * 100);
        int roomNumberEnd = (int) ((hotelEntity.getId() + 1) * 100 - 1);

        if (updatedRoom.getRoomNr() < roomNumberStart || updatedRoom.getRoomNr() > roomNumberEnd) {
            throw new GeneralException("Room number must be between " + roomNumberStart + " and " + roomNumberEnd + " for this hotel");
        }
        RoomDTO roomDTO = ROOM_MAPPER.toDto(roomToBeUpdated);
        roomDTO.setHotel(HOTEL_MAPPER.toDto(hotelEntity));
        roomDTO.setRoomType(ROOM_TYPE_MAPPER.toDto(roomTypeEntity));
        roomDTO.setRoomNr(updatedRoom.getRoomNr());

        return ROOM_MAPPER.toDto(roomRepository.save(ROOM_MAPPER.toEntity(roomDTO)));
    }

    @Override
    public List<RoomDTO> getAvailableRooms(LocalDateTime checkIn, LocalDateTime checkOut) {
        List<RoomEntity> roomsBooked = new ArrayList<>();
        List<BookingEntity> bookings = bookingRepository.findAllByCheckInTimeAfterAndCheckOutTimeBeforeAndDeletedFalse(checkIn, checkOut);

        bookings.forEach(booking -> {
            List<RoomBookedEntity> roomBookedEntities = roomBookedRepository.findRoomBookedByBookingIdAndDeletedFalse(booking.getId());
            roomBookedEntities.forEach(roomBookedEntity -> {
                roomsBooked.add(roomBookedEntity.getRoom());
            });
        });

        List<RoomEntity> allRoomsAvailable = roomRepository.findAll();
        for (RoomEntity roomBooked : roomsBooked) {
            allRoomsAvailable.remove(roomBooked);
        }

        return allRoomsAvailable.stream().map(ROOM_MAPPER::toDto).collect(Collectors.toList());
    }

    @Override
    public void deleteRoom(Long id) {

        RoomEntity roomToBeDeleted = roomRepository.findById(id).orElseThrow(() -> new GeneralException("No room with id: " + id + " was found"));

        if (roomToBeDeleted.getDeleted()) {
            throw new GeneralException("No room with id: " + id + " was found on the db");
        }
        List<RoomBookedEntity> roomsBooked = roomBookedRepository.findAllNonDeleted();
        List<RoomBookedEntity> activeBookings = roomsBooked.stream().filter(roomBooked -> roomBooked.getBooking().getCheckInTime().isAfter(LocalDateTime.now()) ||
                roomBooked.getBooking().getCheckInTime().isBefore(LocalDateTime.now())).toList();

        boolean flag = true;
        for (RoomBookedEntity roomBooked : activeBookings) {
            if (roomBooked.getRoom().equals(roomToBeDeleted)) {
                flag = false;
            }
        }

        if (!flag) {
            throw new GeneralException("You can not delete a room with active bookings");
        }
        RoomDTO roomDTO = ROOM_MAPPER.toDto(roomToBeDeleted);
        roomDTO.setDeleted(true);

        roomRepository.save(ROOM_MAPPER.toEntity(roomDTO));
    }
}
