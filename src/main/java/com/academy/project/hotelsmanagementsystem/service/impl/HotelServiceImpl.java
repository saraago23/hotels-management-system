package com.academy.project.hotelsmanagementsystem.service.impl;

import com.academy.project.hotelsmanagementsystem.dto.HotelDTO;
import com.academy.project.hotelsmanagementsystem.dto.PageDTO;
import com.academy.project.hotelsmanagementsystem.entity.HotelEntity;
import com.academy.project.hotelsmanagementsystem.exceptions.GeneralException;
import com.academy.project.hotelsmanagementsystem.repository.HotelRepository;
import com.academy.project.hotelsmanagementsystem.service.HotelService;
import com.academy.project.hotelsmanagementsystem.utils.UserUtils;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;

import static com.academy.project.hotelsmanagementsystem.mapper.RoomMapper.ROOM_MAPPER;
import static com.academy.project.hotelsmanagementsystem.utils.PageUtils.*;
import static com.academy.project.hotelsmanagementsystem.mapper.HotelMapper.*;
@Validated
@Service
public class HotelServiceImpl implements HotelService {
    @Autowired
    private HotelRepository hotelRepository;
    @Override
    public PageDTO<HotelDTO> findAll(Pageable pageable) {
        return toPageImpl(hotelRepository.findAll(pageable),HOTEL_MAPPER);
    }

    @Override
    public HotelDTO addHotel(@Valid HotelDTO hotelDTO) {
        var entity=hotelRepository.save(HOTEL_MAPPER.toEntity(hotelDTO));
        return HOTEL_MAPPER.toDto(entity);
    }

    @Override
    public HotelDTO findHotelById(Long id) {

        HotelEntity hotelEntity = hotelRepository.findById(id).orElseThrow(()->new GeneralException("Hotel with id: " + " does not exist"));

        if (hotelEntity.getDeleted()) {
            throw new GeneralException("This booking no longer exists");
        }

        if (UserUtils.getLoggedUser().equals("o")) {
            throw new GeneralException("You are not allowed to access this feature!");
        }
        return HOTEL_MAPPER.toDto(hotelEntity);
    }

    @Override
    @Transactional
    public HotelDTO updateHotel(Long id, @Valid HotelDTO updatedHotel) {
        if (hotelRepository.existsById(id)) {
            updatedHotel.setId(id);
            return HOTEL_MAPPER.toDto(hotelRepository.save(HOTEL_MAPPER.toEntity(updatedHotel)));
        }
        throw new GeneralException("Hotel with id: " + id + " was not found");
    }

    @Override
    public void deleteHotel(Long id) {

    }
}
