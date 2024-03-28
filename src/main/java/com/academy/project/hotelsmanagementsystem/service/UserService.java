package com.academy.project.hotelsmanagementsystem.service;

import com.academy.project.hotelsmanagementsystem.dto.PageDTO;
import com.academy.project.hotelsmanagementsystem.dto.UserDTO;
import com.academy.project.hotelsmanagementsystem.entity.UserEntity;
import org.springframework.data.domain.Pageable;

public interface UserService {

    PageDTO<UserDTO> findAll(Pageable pageable);
    UserDTO findUserById (Long id);
    UserDTO createUser(String role,UserDTO userDTO);
    UserDTO updateUser (Long id,UserDTO userDTO);
    void deleteUser (Long id);

}
