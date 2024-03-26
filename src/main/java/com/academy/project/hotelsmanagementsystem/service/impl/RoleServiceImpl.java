package com.academy.project.hotelsmanagementsystem.service.impl;

import com.academy.project.hotelsmanagementsystem.dto.PageDTO;
import com.academy.project.hotelsmanagementsystem.dto.RoleDTO;
import com.academy.project.hotelsmanagementsystem.entity.RoleEntity;
import com.academy.project.hotelsmanagementsystem.exceptions.GeneralException;
import com.academy.project.hotelsmanagementsystem.repository.RoleRepository;
import com.academy.project.hotelsmanagementsystem.service.RoleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import static com.academy.project.hotelsmanagementsystem.utils.PageUtils.*;
import static com.academy.project.hotelsmanagementsystem.mapper.RoleMapper.*;

@Service
@Validated
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public PageDTO<RoleDTO> findAll(Pageable pageable) {
        return toPageImpl(roleRepository.findAll(pageable), ROLE_MAPPER);
    }

    @Override
    public RoleDTO addRole(@Valid RoleDTO roleDTO) {
        var entity = roleRepository.save(ROLE_MAPPER.toEntity(roleDTO));
        return ROLE_MAPPER.toDto(entity);
    }

    @Override
    public RoleDTO findRoleById(Long id) {
        RoleEntity roleEntity= roleRepository.findById(id).orElseThrow(()-> new GeneralException("Role with id: " + id + " was not found"));
        return ROLE_MAPPER.toDto(roleEntity);
    }

    @Override
    public RoleDTO updateRole(Long id, @Valid RoleDTO updatedRole) {
        if (roleRepository.existsById(id)) {
            updatedRole.setId(id);
            return ROLE_MAPPER.toDto(roleRepository.save(ROLE_MAPPER.toEntity(updatedRole)));
        }
        throw new GeneralException("Role with id: " + id + " was not found");
    }

    @Override
    public void deleteRole(Long id) {

    }
}
