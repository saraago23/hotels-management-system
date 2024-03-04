package com.academy.project.hotelsmanagementsystem.service;

import com.academy.project.hotelsmanagementsystem.dto.HotelDTO;
import com.academy.project.hotelsmanagementsystem.dto.PageDTO;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface HotelService {

    PageDTO<HotelDTO> findAll(Pageable pageable);
    HotelDTO addHotel(@Valid HotelDTO req);
    Optional<HotelDTO> findHotelById(Integer id);
    HotelDTO updateHotel(Integer id,@Valid HotelDTO req);
    void deleteHotel(Integer id);
}
