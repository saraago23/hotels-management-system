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

    @Override
    public PageDTO<RoleDTO> findAll(Pageable pageable) {
        return toPageImpl(roleRepository.findAllNonDeleted(pageable), ROLE_MAPPER);
    }



    @Override
    public RoleDTO addRole(@Valid RoleDTO roleDTO) {
        RoleEntity newRole=RoleEntity.builder()
                .title(roleDTO.getTitle())
                .description(roleDTO.getDescription())
                .deleted(false)
                .build();

        return ROLE_MAPPER.toDto(roleRepository.save(newRole));
    }

    @Override
    public RoleDTO findRoleById(Long id) {
        RoleEntity roleEntity = roleRepository.findById(id).orElseThrow(() -> new GeneralException("Role with id: " + id + " was not found"));
        if(roleEntity.getDeleted()){
            throw new GeneralException("No role with id: " + id + " was found");
        }
        return ROLE_MAPPER.toDto(roleEntity);
    }

    @Override
    public RoleDTO updateRole(Long id, @Valid RoleDTO updatedRole) {

        RoleEntity roleToBeUpdated = roleRepository.findById(id).orElseThrow(() -> new GeneralException("Role with id: " + id + " was not found"));
        roleToBeUpdated.setDescription(updatedRole.getDescription());

        return ROLE_MAPPER.toDto(roleRepository.save(ROLE_MAPPER.toEntity(updatedRole)));

    }

    @Override
    public void deleteRole(Long id) {
        RoleEntity roleToBeUpdated = roleRepository.findById(id).orElseThrow(() -> new GeneralException("Role with id: " + id + " was not found"));
        if (userRepository.findByRoleId(id)!=null){
            throw new GeneralException("You can not delete a role with existing users");
        }
        roleToBeUpdated.setDeleted(true);

    }
}
