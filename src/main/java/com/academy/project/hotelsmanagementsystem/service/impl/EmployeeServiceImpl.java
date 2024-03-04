package com.academy.project.hotelsmanagementsystem.service.impl;

import com.academy.project.hotelsmanagementsystem.dto.EmployeeDTO;
import com.academy.project.hotelsmanagementsystem.dto.PageDTO;
import com.academy.project.hotelsmanagementsystem.repository.EmployeeRepository;
import com.academy.project.hotelsmanagementsystem.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.Optional;

import static com.academy.project.hotelsmanagementsystem.utils.PageUtils.*;
import static com.academy.project.hotelsmanagementsystem.mapper.EmployeeMapper.*;

@Service

public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;
    @Override
    public PageDTO<EmployeeDTO> findAll(Pageable pageable) {
        return toPageImpl(employeeRepository.findAll(pageable),EMPLOYEE_MAPPER);
    }

    @Override
    public EmployeeDTO addEmployee(@Valid EmployeeDTO req) {
        var entity=employeeRepository.save(EMPLOYEE_MAPPER.toEntity(req));
        return EMPLOYEE_MAPPER.toDto(entity);
    }

    @Override
    public Optional<EmployeeDTO> findEmployeeById(Integer id) {
        return employeeRepository.findById(id)
                .map(EMPLOYEE_MAPPER::toDto);
    }

    @Override
    public EmployeeDTO updateEmployee(Integer id, @Valid EmployeeDTO req) {
        req.setId(id);
        return employeeRepository.findById(id)
                .map(o->EMPLOYEE_MAPPER.toEntity(req))
                .map(employeeRepository::save)
                .map(EMPLOYEE_MAPPER::toDto)
                .orElse(null);
    }

    @Override
    public void deleteEmployee(Integer id) {

    }
}
