package com.academy.project.hotelsmanagementsystem.service.impl;

import com.academy.project.hotelsmanagementsystem.dto.PageDTO;
import com.academy.project.hotelsmanagementsystem.dto.UserDTO;
import com.academy.project.hotelsmanagementsystem.entity.BookingEntity;
import com.academy.project.hotelsmanagementsystem.entity.RoleEntity;
import com.academy.project.hotelsmanagementsystem.entity.UserEntity;
import com.academy.project.hotelsmanagementsystem.exceptions.GeneralException;
import com.academy.project.hotelsmanagementsystem.repository.BookingRepository;
import com.academy.project.hotelsmanagementsystem.repository.RoleRepository;
import com.academy.project.hotelsmanagementsystem.repository.UserRepository;
import com.academy.project.hotelsmanagementsystem.service.UserService;
import com.academy.project.hotelsmanagementsystem.utils.UserUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.academy.project.hotelsmanagementsystem.utils.PageUtils.*;
import static com.academy.project.hotelsmanagementsystem.mapper.UserMapper.*;


@Service
@RequiredArgsConstructor
//@Validated
public class UserServiceImpl implements UserService, UserDetailsService {


    private final UserRepository userRepository;
    private final BookingRepository bookingRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public PageDTO<UserDTO> findAll(Pageable pageable) {
        return toPageImpl(userRepository.findAllNonDeleted(pageable), USER_MAPPER);
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
    public UserDTO createUser(String role, @Valid UserDTO userDTO) {
        UserEntity userEntity = USER_MAPPER.toEntity(userDTO);

        var roleEntity = roleRepository.findByRoleTitle(role);

        if (roleEntity == null) {
            String availableRoles = roleRepository.findAll().stream()
                    .map(RoleEntity::getTitle)
                    .collect(Collectors.joining(", "));
            throw new GeneralException("Please enter one of the roles: " + availableRoles);
        }

        boolean existingAdmin = roleRepository.findAll().stream().anyMatch(roleEntity1 -> roleEntity1.getTitle().equalsIgnoreCase("ADMIN") && roleEntity1.getDeleted().equals(false));

        if (roleEntity.getTitle().equalsIgnoreCase("ADMIN") && existingAdmin) {
            throw new GeneralException("There already is an ADMIN on the system");
        }

        userEntity.setRole(roleEntity);
        userEntity.setFirstName(userDTO.getFirstName());
        userEntity.setLastName(userDTO.getLastName());
        userEntity.setAddress(userDTO.getAddress());
        userEntity.setEmail(userDTO.getEmail());
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

        UserEntity userEntity = userRepository.findByIdAndDeletedFalse(id).orElseThrow(() -> new GeneralException("User with id: " + id + " was not found"));

        if (!(UserUtils.getLoggedUserRole().contains("ADMIN") || UserUtils.getLoggedUser().equals(userEntity.getUsername()))) {
            throw new GeneralException("You have no access to edit this user");
        }
        UserDTO userDTO1 = USER_MAPPER.toDto(userEntity);

        userDTO1.setFirstName(userDTO.getFirstName());
        userDTO1.setLastName(userDTO.getLastName());
        userDTO1.setAddress(userDTO.getAddress());
        userDTO1.setEmail(userDTO.getEmail());
        userDTO1.setBirthDate(userDTO.getBirthDate());
        userDTO1.setPhone(userDTO.getPhone());
        userDTO1.setGender(userDTO.getGender());
        userDTO1.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        userDTO1.setDeleted(false);

        return USER_MAPPER.toDto(userRepository.save(USER_MAPPER.toEntity(userDTO1)));
    }

    @Override
    public void deleteUser(Long id) {
        UserEntity userToBeDeleted = userRepository.findByIdAndDeletedFalse(id).orElseThrow(() -> new GeneralException("User with id: " + id + " was not found"));

        if (!(UserUtils.getLoggedUserRole().contains("ADMIN") || UserUtils.getLoggedUser().equals(userToBeDeleted.getUsername()))) {
            throw new GeneralException("You have no access over this user");
        }

        List<BookingEntity> userBookings = bookingRepository.findBookingsByUserAndDeletedFalse(userToBeDeleted);

        boolean flag = true;
        for (BookingEntity booking : userBookings) {
            if (LocalDateTime.now().isBefore(booking.getCheckInTime()) || LocalDateTime.now().isAfter(booking.getCheckOutTime())) {
                flag = false;
            }
        }

        if (!flag) {
            throw new GeneralException("Can not delete user with active bookings.");
        }
        UserDTO userDTO = USER_MAPPER.toDto(userToBeDeleted);

        userDTO.setDeleted(true);
        userRepository.save(USER_MAPPER.toEntity(userDTO));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsernameAndDeletedFalse(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with username " + username + " not found"));
    }

}
