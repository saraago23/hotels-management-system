package com.academy.project.hotelsmanagementsystem.service.impl;

import com.academy.project.hotelsmanagementsystem.dto.HotelDTO;
import com.academy.project.hotelsmanagementsystem.dto.PageDTO;
import com.academy.project.hotelsmanagementsystem.repository.HotelRepository;
import com.academy.project.hotelsmanagementsystem.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;
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
    public HotelDTO addHotel(HotelDTO req) {
        var entity=hotelRepository.save(HOTEL_MAPPER.toEntity(req));
        return HOTEL_MAPPER.toDto(entity);
    }

    @Override
    public Optional<HotelDTO> findHotelById(Long id) {
        return hotelRepository.findById(id)
                .map(HOTEL_MAPPER::toDto);
    }

    @Override
    public HotelDTO updateHotel(Long id, HotelDTO req) {
        req.setId(id);
        return hotelRepository.findById(id)
                .map(o->HOTEL_MAPPER.toEntity(req))
                .map(hotelRepository::save)
                .map(HOTEL_MAPPER::toDto)
                .orElse(null);
    }

    @Override
    public void deleteHotel(Long id) {

    }
}
