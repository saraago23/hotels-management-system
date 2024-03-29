package com.academy.project.hotelsmanagementsystem.service;

import com.academy.project.hotelsmanagementsystem.dto.PageDTO;
import com.academy.project.hotelsmanagementsystem.dto.RoleDTO;
import com.academy.project.hotelsmanagementsystem.entity.RoleEntity;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface RoleService {
    PageDTO<RoleDTO> findAllNonDeleted(Pageable pageable);
    PageDTO<RoleDTO> findAllDeleted(Pageable pageable);

    RoleDTO addRole(@Valid RoleDTO roleDTO);
    RoleDTO findRoleById(Long id);
    RoleDTO updateRole(Long id,@Valid RoleDTO updatedRole);
    void deleteRole(Long id);
}
