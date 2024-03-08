package com.academy.project.hotelsmanagementsystem.service;

import com.academy.project.hotelsmanagementsystem.dto.PageDTO;
import com.academy.project.hotelsmanagementsystem.dto.RoleDTO;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface RoleService {
    PageDTO<RoleDTO> findAll(Pageable pageable);

    RoleDTO addRole(@Valid RoleDTO req);
    Optional<RoleDTO> findRoleById(Long id);
    RoleDTO updateRole(Long id,@Valid RoleDTO req);
    void deleteRole(Long id);
}
