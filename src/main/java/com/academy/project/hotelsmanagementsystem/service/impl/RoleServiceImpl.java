package com.academy.project.hotelsmanagementsystem.service.impl;

import com.academy.project.hotelsmanagementsystem.dto.PageDTO;
import com.academy.project.hotelsmanagementsystem.dto.RoleDTO;
import com.academy.project.hotelsmanagementsystem.entity.RoleEntity;
import com.academy.project.hotelsmanagementsystem.repository.RoleRepository;
import com.academy.project.hotelsmanagementsystem.service.RoleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;

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
    public RoleDTO addRole(@Valid RoleDTO req) {
        var entity = roleRepository.save(ROLE_MAPPER.toEntity(req));
        return ROLE_MAPPER.toDto(entity);
    }

    @Override
    public Optional<RoleEntity> findRoleById(Integer id) {
        return roleRepository.findById(id);
    }

    @Override
    public RoleDTO updateRole(Integer id, @Valid RoleDTO req) {
        req.setId(id);
        return roleRepository.findById(id)
                .map(o -> ROLE_MAPPER.toEntity(req))
                .map(roleRepository::save)
                .map(ROLE_MAPPER::toDto)
                .orElse(null);
    }

    @Override
    public void deleteRole(Integer id) {

    }
}
