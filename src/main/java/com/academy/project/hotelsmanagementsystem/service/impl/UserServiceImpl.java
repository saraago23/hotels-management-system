package com.academy.project.hotelsmanagementsystem.service.impl;

import com.academy.project.hotelsmanagementsystem.dto.PageDTO;
import com.academy.project.hotelsmanagementsystem.dto.UserDTO;
import com.academy.project.hotelsmanagementsystem.repository.UserRepository;
import com.academy.project.hotelsmanagementsystem.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import static com.academy.project.hotelsmanagementsystem.utils.PageUtils.*;
import static com.academy.project.hotelsmanagementsystem.mapper.UserMapper.*;

import java.util.Optional;

@Service
@Validated
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;


    @Override
    public PageDTO<UserDTO> findAll(Pageable pageable) {
        return toPageImpl(userRepository.findAll(pageable),USER_MAPPER);
    }

    @Override
    public UserDTO addUser(@Valid UserDTO req) {
        var entity=userRepository.save(USER_MAPPER.toEntity(req));
        return USER_MAPPER.toDto(entity);
    }

    @Override
    public Optional<UserDTO> findUserById(Long id) {
        return userRepository.findById(id)
                .map(USER_MAPPER::toDto);
    }

    @Override
    public UserDTO updateUser(Long id, @Valid UserDTO req) {
        req.setId(id);
        return userRepository.findById(id)
                .map(o->USER_MAPPER.toEntity(req))
                .map(userRepository::save)
                .map(USER_MAPPER::toDto)
                .orElse(null);
    }

    @Override
    public void deleteUser(Long id) {

    }
}
