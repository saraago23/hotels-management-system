package com.academy.project.hotelsmanagementsystem.mapper;

import com.academy.project.hotelsmanagementsystem.dto.RoleDTO;
import com.academy.project.hotelsmanagementsystem.entity.RoleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
@Mapper
public interface RoleMapper extends BaseMapper<RoleEntity, RoleDTO>{
    RoleMapper ROLE_MAPPER= Mappers.getMapper(RoleMapper.class);
}
