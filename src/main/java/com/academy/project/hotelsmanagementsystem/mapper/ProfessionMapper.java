package com.academy.project.hotelsmanagementsystem.mapper;

import com.academy.project.hotelsmanagementsystem.dto.ProfessionDTO;
import com.academy.project.hotelsmanagementsystem.entity.ProfessionEntity;
import org.mapstruct.factory.Mappers;

public interface ProfessionMapper extends BaseMapper<ProfessionEntity, ProfessionDTO>{
    ProfessionMapper PROFESSION_MAPPER= Mappers.getMapper(ProfessionMapper.class);
}
