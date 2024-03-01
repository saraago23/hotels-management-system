package com.academy.project.hotelsmanagementsystem.mapper;

import com.academy.project.hotelsmanagementsystem.dto.EmployeeDTO;
import com.academy.project.hotelsmanagementsystem.entity.EmployeeEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface EmployeeMapper extends BaseMapper<EmployeeEntity, EmployeeDTO>{
    EmployeeMapper EMPLOYEE_MAPPER= Mappers.getMapper(EmployeeMapper.class);
}
