package com.academy.project.hotelsmanagementsystem.service;

import com.academy.project.hotelsmanagementsystem.dto.EmployeeDTO;
import com.academy.project.hotelsmanagementsystem.dto.PageDTO;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;


import java.util.Optional;

public interface EmployeeService {
    PageDTO<EmployeeDTO> findAll(Pageable pageable);
    EmployeeDTO addEmployee(@Valid EmployeeDTO req);
    Optional<EmployeeDTO> findEmployeeById(Integer id);
    EmployeeDTO updateEmployee(Integer id, @Valid EmployeeDTO req);
    void deleteEmployee(Integer id);
}
