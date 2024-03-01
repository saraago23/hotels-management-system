package com.academy.project.hotelsmanagementsystem.mapper;

import com.academy.project.hotelsmanagementsystem.dto.PersonDTO;
import com.academy.project.hotelsmanagementsystem.entity.PersonEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
@Mapper
public interface PersonMapper extends BaseMapper<PersonEntity, PersonDTO>{
    PersonMapper PERSON_MAPPER= Mappers.getMapper(PersonMapper.class);
}
