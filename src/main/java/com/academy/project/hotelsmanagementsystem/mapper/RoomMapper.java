package com.academy.project.hotelsmanagementsystem.mapper;

import com.academy.project.hotelsmanagementsystem.dto.RoomDTO;
import com.academy.project.hotelsmanagementsystem.entity.RoomEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
@Mapper
public interface RoomMapper extends BaseMapper<RoomEntity, RoomDTO>{
    RoomMapper ROOM_MAPPER= Mappers.getMapper(RoomMapper.class);
}
