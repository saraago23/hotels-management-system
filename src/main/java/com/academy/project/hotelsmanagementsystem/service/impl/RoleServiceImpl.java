package com.academy.project.hotelsmanagementsystem.service.impl;

import com.academy.project.hotelsmanagementsystem.dto.PageDTO;
import com.academy.project.hotelsmanagementsystem.dto.RoleDTO;
import com.academy.project.hotelsmanagementsystem.entity.RoleEntity;
import com.academy.project.hotelsmanagementsystem.exceptions.GeneralException;
import com.academy.project.hotelsmanagementsystem.repository.RoleRepository;
import com.academy.project.hotelsmanagementsystem.repository.UserRepository;
import com.academy.project.hotelsmanagementsystem.service.RoleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
    @Autowired
    private UserRepository userRepository;

    //admin
    @Override
    public PageDTO<RoleDTO> findAllNonDeleted(Pageable pageable) {
        return toPageImpl(roleRepository.findAllNonDeleted(pageable), ROLE_MAPPER);
    }

    //admin
    @Override
    public PageDTO<RoleDTO> findAllDeleted(Pageable pageable) {
        return toPageImpl(roleRepository.findAllDeleted(pageable), ROLE_MAPPER);
    }

    //admin
    @Override
    public RoleDTO addRole(@Valid RoleDTO roleDTO) {
        RoleEntity newRole = RoleEntity.builder()
                .title(roleDTO.getTitle())
                .description(roleDTO.getDescription())
                .deleted(false)
                .build();

        return ROLE_MAPPER.toDto(roleRepository.save(newRole));
    }

    //admin
    @Override
    public RoleDTO findRoleById(Long id) {
        RoleEntity roleEntity = roleRepository.findById(id).orElseThrow(() -> new GeneralException("Role with id: " + id + " was not found"));
        if (roleEntity.getDeleted()) {
            throw new GeneralException("No role with id: " + id + " was found");
        }
        return ROLE_MAPPER.toDto(roleEntity);
    }

    //admin
    @Override
    public RoleDTO updateRole(Long id, @Valid RoleDTO updatedRole) {
        RoleEntity roleToBeUpdated = roleRepository.findById(id).orElseThrow(() -> new GeneralException("Role with id: " + id + " was not found"));

        if (roleToBeUpdated.getDeleted()) {
            throw new GeneralException("No role with id: " + id + " was found on the db");
        }

        roleToBeUpdated.setDescription(updatedRole.getDescription());

        return ROLE_MAPPER.toDto(roleRepository.save(roleToBeUpdated));

    }

    //admin
    @Override
    public void deleteRole(Long id) {
        RoleEntity roleToBeDeleted = roleRepository.findById(id).orElseThrow(() -> new GeneralException("Role with id: " + id + " was not found"));

        if (roleToBeDeleted.getDeleted()) {
            throw new GeneralException("No role with id: " + id + " was found on the db");
        }

        if (!userRepository.findByRoleId(id).isEmpty()) {
            throw new GeneralException("You can not delete a role with existing users");
        }
        roleToBeDeleted.setDeleted(true);
        roleRepository.save(roleToBeDeleted);

    }
}
