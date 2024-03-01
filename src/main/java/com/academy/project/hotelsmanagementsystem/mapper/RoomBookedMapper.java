package com.academy.project.hotelsmanagementsystem.mapper;

import com.academy.project.hotelsmanagementsystem.dto.RoomBookedDTO;
import com.academy.project.hotelsmanagementsystem.entity.RoomBookedEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
@Mapper
public interface RoomBookedMapper extends BaseMapper<RoomBookedEntity, RoomBookedDTO>{
    RoomBookedMapper ROOM_BOOKED_MAPPER= Mappers.getMapper(RoomBookedMapper.class);
}
