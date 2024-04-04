package com.academy.project.hotelsmanagementsystem.service.impl;

import com.academy.project.hotelsmanagementsystem.dto.PageDTO;
import com.academy.project.hotelsmanagementsystem.dto.RoleDTO;
import com.academy.project.hotelsmanagementsystem.entity.RoleEntity;
import com.academy.project.hotelsmanagementsystem.exceptions.GeneralException;
import com.academy.project.hotelsmanagementsystem.repository.RoleRepository;
import com.academy.project.hotelsmanagementsystem.repository.UserRepository;
import com.academy.project.hotelsmanagementsystem.service.RoleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
        RoleEntity roleEntity = roleRepository.findByIdAndDeletedFalse(id).orElseThrow(() -> new GeneralException("Role with id: " + id + " was not found"));

        return ROLE_MAPPER.toDto(roleEntity);
    }

    //admin
    @Override
    public RoleDTO updateRole(Long id, @Valid RoleDTO updatedRole) {
        RoleEntity roleToBeUpdated = roleRepository.findByIdAndDeletedFalse(id).orElseThrow(() -> new GeneralException("Role with id: " + id + " was not found"));

        RoleDTO roleDTO = ROLE_MAPPER.toDto(roleToBeUpdated);
        roleDTO.setDescription(updatedRole.getDescription());

        return ROLE_MAPPER.toDto(roleRepository.save(ROLE_MAPPER.toEntity(roleDTO)));

    }

    //admin
    @Override
    public void deleteRole(Long id) {
        RoleEntity roleToBeDeleted = roleRepository.findByIdAndDeletedFalse(id).orElseThrow(() -> new GeneralException("Role with id: " + id + " was not found"));

        if (!userRepository.findByRoleIdAndDeletedFalse(id).isEmpty()) {
            throw new GeneralException("You can not delete a role with existing users");
        }

        RoleDTO roleDTO = ROLE_MAPPER.toDto(roleToBeDeleted);

        roleDTO.setDeleted(true);
        roleRepository.save(ROLE_MAPPER.toEntity(roleDTO));

    }
}
