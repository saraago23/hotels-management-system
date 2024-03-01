package com.academy.project.hotelsmanagementsystem.service.impl;

import com.academy.project.hotelsmanagementsystem.dto.PageDTO;
import com.academy.project.hotelsmanagementsystem.dto.ProfessionDTO;
import com.academy.project.hotelsmanagementsystem.repository.ProfessionRepository;
import com.academy.project.hotelsmanagementsystem.service.ProfessionService;
import com.academy.project.hotelsmanagementsystem.utils.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;

import static com.academy.project.hotelsmanagementsystem.mapper.ProfessionMapper.*;

@Service
@Validated
public class ProfessionServiceImpl implements ProfessionService {
    @Autowired
    private ProfessionRepository professionRepository;

    @Override
    public PageDTO<ProfessionDTO> findAll(Pageable pageable) {
        return PageUtils.toPageImpl(professionRepository.findAll(pageable), PROFESSION_MAPPER);
    }

    @Override
    public ProfessionDTO addProfession(ProfessionDTO req) {
        var entity = professionRepository.save(PROFESSION_MAPPER.toEntity(req));
        return PROFESSION_MAPPER.toDto(entity);
    }

    @Override
    public Optional<ProfessionDTO> findProfessionById(Integer id) {
        return professionRepository.findById(id)
                .map(PROFESSION_MAPPER::toDto);

    }

    @Override
    public ProfessionDTO updateProfession(Integer id, ProfessionDTO req) {
        req.setId(id);
        return professionRepository.findById(id)
                .map(o-> PROFESSION_MAPPER.toEntity(req))
                .map(professionRepository::save)
                .map(PROFESSION_MAPPER::toDto)
                .orElse(null);
    }

    @Override
    public void deleteProfession(Integer id) {

    }
}
