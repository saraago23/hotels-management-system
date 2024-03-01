package com.academy.project.hotelsmanagementsystem.service;

import com.academy.project.hotelsmanagementsystem.dto.PageDTO;
import com.academy.project.hotelsmanagementsystem.dto.ProfessionDTO;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;


import java.util.Optional;

public interface ProfessionService {

    PageDTO<ProfessionDTO> findAll(Pageable pageable);
    ProfessionDTO addProfession(@Valid ProfessionDTO req);
    Optional<ProfessionDTO> findProfessionById(Integer id);
    ProfessionDTO updateProfession(Integer id,@Valid ProfessionDTO req);
    void deleteProfession(Integer id);
}
