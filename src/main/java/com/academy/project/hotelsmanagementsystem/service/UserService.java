package com.academy.project.hotelsmanagementsystem.service;

import com.academy.project.hotelsmanagementsystem.dto.PageDTO;
import com.academy.project.hotelsmanagementsystem.dto.UserDTO;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface UserService {

    PageDTO<UserDTO> findAll(Pageable pageable);
    UserDTO addUser (@Valid UserDTO req);
    Optional<UserDTO>findUserById(Long id);
    UserDTO updateUser(Long id, @Valid UserDTO req);
    void deleteUser(Long id);
}
