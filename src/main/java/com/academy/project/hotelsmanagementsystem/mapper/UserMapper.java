package com.academy.project.hotelsmanagementsystem.mapper;

import com.academy.project.hotelsmanagementsystem.dto.UserDTO;
import com.academy.project.hotelsmanagementsystem.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper extends BaseMapper<UserEntity, UserDTO>{

    UserMapper USER_MAPPER= Mappers.getMapper(UserMapper.class);
}
