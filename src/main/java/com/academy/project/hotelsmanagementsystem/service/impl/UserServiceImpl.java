package com.academy.project.hotelsmanagementsystem.service.impl;

import com.academy.project.hotelsmanagementsystem.dto.PageDTO;
import com.academy.project.hotelsmanagementsystem.dto.UserDTO;
import com.academy.project.hotelsmanagementsystem.entity.BookingEntity;
import com.academy.project.hotelsmanagementsystem.entity.RoomEntity;
import com.academy.project.hotelsmanagementsystem.entity.UserEntity;
import com.academy.project.hotelsmanagementsystem.exceptions.GeneralException;
import com.academy.project.hotelsmanagementsystem.repository.BookingRepository;
import com.academy.project.hotelsmanagementsystem.repository.RoleRepository;
import com.academy.project.hotelsmanagementsystem.repository.UserRepository;
import com.academy.project.hotelsmanagementsystem.service.UserService;
import com.academy.project.hotelsmanagementsystem.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static com.academy.project.hotelsmanagementsystem.mapper.RoomMapper.ROOM_MAPPER;
import static com.academy.project.hotelsmanagementsystem.utils.PageUtils.*;
import static com.academy.project.hotelsmanagementsystem.mapper.UserMapper.*;


@Service
public class UserServiceImpl implements UserService , UserDetailsService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;


    @Override
    public PageDTO<UserDTO> findAll(Pageable pageable) {
        return toPageImpl(userRepository.findAll(pageable), USER_MAPPER);
    }

    @Override
    public UserDTO findUserById(Long id) {
        UserEntity userEntity = userRepository.findById(id).orElseThrow(() -> new GeneralException("User with id: " + id + " was not found"));

        if (userEntity.getDeleted()) {
            throw new GeneralException("No user with id: " + id + " was found");
        }

        if (!(UserUtils.getLoggedUserRole().contains("ADMIN") || UserUtils.getLoggedUser().equals(userEntity.getUsername()))) {
            throw new GeneralException("You have no access over this user");
        }

        return USER_MAPPER.toDto(userEntity);

    }

    @Override
    public UserDTO createUser(String role, UserDTO userDTO) {
        UserEntity userEntity = USER_MAPPER.toEntity(userDTO);
        var roleTitle = roleRepository.findByRoleTitle(role);
        if (roleTitle == null) {
            throw new GeneralException("Role not found");
        }
        userEntity.setRole(roleTitle);
        userEntity.setFirstName(userDTO.getFirstName());
        userEntity.setLastName(userDTO.getLastName());
        userEntity.setAddress(userDTO.getAddress());
        userEntity.setEmail(userDTO.getEmail());

        if (userDTO.getBirthDate().equals(LocalDate.now()) || userDTO.getBirthDate().isAfter(LocalDate.now())) {
            throw new GeneralException("The birthdate you have entered is not correct");
        }
        userEntity.setBirthDate(userDTO.getBirthDate());
        userEntity.setPhone(userDTO.getPhone());
        userEntity.setGender(userDTO.getGender());
        userEntity.setUsername(userDTO.getUsername());
        userEntity.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        userEntity.setDeleted(false);

        return USER_MAPPER.toDto(userRepository.save(userEntity));
    }

    @Override
    public UserDTO updateUser(Long id, UserDTO userDTO) {

        UserEntity userEntity = userRepository.findById(id).orElseThrow(() -> new GeneralException("User with id: " + id + " was not found"));

        userEntity.setFirstName(userDTO.getFirstName());
        userEntity.setLastName(userDTO.getLastName());
        userEntity.setAddress(userDTO.getAddress());
        userEntity.setEmail(userDTO.getEmail());

        if (userDTO.getBirthDate().equals(LocalDate.now()) || userDTO.getBirthDate().isAfter(LocalDate.now())) {
            throw new GeneralException("The birthdate you have entered is not correct");
        }
        userEntity.setBirthDate(userDTO.getBirthDate());
        userEntity.setPhone(userDTO.getPhone());
        userEntity.setGender(userDTO.getGender());
        userEntity.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        userEntity.setDeleted(false);

        return USER_MAPPER.toDto(userRepository.save(USER_MAPPER.toEntity(userDTO)));
    }

    @Override
    public void deleteUser(Long id) {
        UserEntity userEntity = userRepository.findById(id).orElseThrow(() -> new GeneralException("User with id: " + id + " was not found"));
        List<BookingEntity> userBookings = bookingRepository.findBookingsByUser(userEntity);
        boolean flag = true;
        for (BookingEntity booking : userBookings) {
            if (LocalDateTime.now().isBefore(booking.getCheckInTime()) || LocalDateTime.now().isAfter(booking.getCheckOutTime())) {
                flag = false;
            }
        }
        if (!flag) {
            throw new GeneralException("Can not delete user with active bookings.");
        }

        if (!(UserUtils.getLoggedUserRole().contains("ADMIN") || UserUtils.getLoggedUser().equals(userEntity.getUsername()))) {
            throw new GeneralException("You have no access over this user");
        }
        userEntity.setDeleted(true);
        userRepository.save(userEntity);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(()->new UsernameNotFoundException("User with username "+username+" not found"));
    }
}
