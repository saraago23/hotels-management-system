package com.academy.project.hotelsmanagementsystem.service.impl;

import com.academy.project.hotelsmanagementsystem.dto.HotelDTO;
import com.academy.project.hotelsmanagementsystem.dto.PageDTO;
import com.academy.project.hotelsmanagementsystem.entity.HotelEntity;
import com.academy.project.hotelsmanagementsystem.entity.RoomBookedEntity;
import com.academy.project.hotelsmanagementsystem.exceptions.GeneralException;
import com.academy.project.hotelsmanagementsystem.repository.HotelRepository;
import com.academy.project.hotelsmanagementsystem.repository.RoomBookedRepository;
import com.academy.project.hotelsmanagementsystem.service.HotelService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.academy.project.hotelsmanagementsystem.mapper.RoomMapper.ROOM_MAPPER;
import static com.academy.project.hotelsmanagementsystem.utils.PageUtils.*;
import static com.academy.project.hotelsmanagementsystem.mapper.HotelMapper.*;


@Service
@Validated
@RequiredArgsConstructor
public class HotelServiceImpl implements HotelService {

    private final HotelRepository hotelRepository;

    private final RoomBookedRepository roomBookedRepository;

    @Override
    public PageDTO<HotelDTO> findAll(Pageable pageable) {
        return toPageImpl(hotelRepository.findAllNonDeleted(pageable), HOTEL_MAPPER);
    }

    @Override
    public HotelDTO addHotel(@Valid HotelDTO hotelDTO) {
        var entity = hotelRepository.save(HOTEL_MAPPER.toEntity(hotelDTO));
        return HOTEL_MAPPER.toDto(entity);
    }

    @Override
    public HotelDTO findHotelById(Long id) {

        HotelEntity hotelEntity = hotelRepository.findByIdAndDeletedFalse(id).orElseThrow(() -> new GeneralException("Hotel with id: " + " does not exist"));

        return HOTEL_MAPPER.toDto(hotelEntity);
    }

    @Override
    @Transactional
    public HotelDTO updateHotel(Long id, @Valid HotelDTO updatedHotel) {
        HotelEntity hotelToBeUpdated = hotelRepository.findByIdAndDeletedFalse(id).orElseThrow(() -> new GeneralException("Hotel with id: " + id + " was not found"));

        HotelDTO hotelToBeUpdatedDto = HOTEL_MAPPER.toDto(hotelToBeUpdated);
        hotelToBeUpdatedDto.setHotelName(updatedHotel.getHotelName());
        hotelToBeUpdatedDto.setAddress(updatedHotel.getAddress());
        hotelToBeUpdatedDto.setPostCode(updatedHotel.getPostCode());
        hotelToBeUpdatedDto.setCity(updatedHotel.getCity());
        hotelToBeUpdatedDto.setCountry(updatedHotel.getCountry());


        return HOTEL_MAPPER.toDto(hotelRepository.save(HOTEL_MAPPER.toEntity(hotelToBeUpdatedDto)));

    }

    @Override
    public void deleteHotel(Long id) {

        HotelEntity hotelToBeDeleted = hotelRepository.findByIdAndDeletedFalse(id).orElseThrow(() -> new GeneralException("Hotel with id: " + id + " was not found"));

        List<RoomBookedEntity> roomsBooked = roomBookedRepository.findRoomBookedEntitiesByHotelIdAndDeletedFalse(id);

        boolean hasActiveBookings = roomsBooked.stream()
                .anyMatch(roomBooked -> roomBooked.getBooking().getCheckInTime().isAfter(LocalDateTime.now())||
                        roomBooked.getBooking().getCheckInTime().isBefore(LocalDateTime.now()));

        if (hasActiveBookings) {
            throw new GeneralException("You can not delete hotels with active bookings");
        }
        HotelDTO hotelDTO=HOTEL_MAPPER.toDto(hotelToBeDeleted);
        hotelDTO.setDeleted(true);

        hotelRepository.save(HOTEL_MAPPER.toEntity(hotelDTO));
    }
}
