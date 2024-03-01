package com.academy.project.hotelsmanagementsystem.mapper;

import com.academy.project.hotelsmanagementsystem.dto.RoomTypeDTO;
import com.academy.project.hotelsmanagementsystem.entity.RoomTypeEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
@Mapper
public interface RoomTypeMapper extends BaseMapper<RoomTypeEntity, RoomTypeDTO> {
    RoomTypeMapper ROOM_TYPE_MAPPER= Mappers.getMapper(RoomTypeMapper.class);
}
