package com.academy.project.hotelsmanagementsystem.service.impl;

import com.academy.project.hotelsmanagementsystem.dto.PageDTO;
import com.academy.project.hotelsmanagementsystem.dto.UserDTO;
import com.academy.project.hotelsmanagementsystem.repository.UserRepository;
import com.academy.project.hotelsmanagementsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;

@Service
@Validated
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;


    @Override
    public PageDTO<UserDTO> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public UserDTO addUser(UserDTO req) {
        return null;
    }

    @Override
    public Optional<UserDTO> findUserById(Integer id) {
        return Optional.empty();
    }

    @Override
    public UserDTO updateUser(Integer id, UserDTO req) {
        return null;
    }

    @Override
    public void deleteUser(Integer id) {

    }
}
