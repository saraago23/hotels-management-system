package com.academy.project.hotelsmanagementsystem.service;

import com.academy.project.hotelsmanagementsystem.dto.BookingDTO;
import com.academy.project.hotelsmanagementsystem.entity.*;
import com.academy.project.hotelsmanagementsystem.exceptions.GeneralException;
import com.academy.project.hotelsmanagementsystem.repository.*;
import com.academy.project.hotelsmanagementsystem.service.impl.BookingServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookingServiceTest {
    @Spy
    @InjectMocks
    BookingServiceImpl toTest;
    @Mock
    private BookingRepository bookingRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private RoomBookedRepository roomBookedRepository;
    @Mock
    private RoomRepository roomRepository;
    @Mock
    SecurityContext securityContext;
    @Mock
    Authentication authentication;

    @Test
    public void test_findAll_ok() {

        var user = new UserEntity();
        user.setId(1L);
        user.setUsername("sara");
        user.setPassword("124567");

        var bookings = Arrays.asList(BookingEntity.builder()
                .id(1L)
                .user(user)
                .bookingTime(LocalDateTime.now())
                .checkInTime(LocalDateTime.now())
                .checkOutTime(LocalDateTime.now().plusDays(1))
                .specialReq("req")
                .totalPrice(BigDecimal.valueOf(100))
                .deleted(false)
                .build());

        Page<BookingEntity> pageEntity = new PageImpl<>(bookings);
        doReturn(pageEntity).when(bookingRepository).findAllNonDeleted(any(Pageable.class));
        var output = toTest.findAll(Pageable.unpaged());
        assertAll(
                () -> assertNotNull(output),
                () -> assertEquals(1, output.getContent().size()),
                () -> assertEquals(1L, output.getContent().get(0).getId())
        );
    }

    @Test
    public void test_findBookingById_ok() {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername("clientUser");
        RoleEntity role = new RoleEntity();
        role.setTitle("CLIENT");
        userEntity.setRole(role);
        when(userRepository.findByUsernameAndDeletedFalse(anyString())).thenReturn(Optional.of(userEntity));

        BookingEntity bookingEntity = new BookingEntity();
        bookingEntity.setId(1L);
        bookingEntity.setUser(userEntity);
        when(bookingRepository.findByIdAndDeletedFalse(anyLong())).thenReturn(Optional.of(bookingEntity));

        when(authentication.getName()).thenReturn("clientUser");
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        BookingDTO result = toTest.findBookingById(1L);

        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(bookingEntity.getId(), result.getId()),
                () -> assertEquals(bookingEntity.getUser().getUsername(), result.getUser().getUsername())
        );

    }

    @Test
    public void test_findBookingById_unauthorizedUser() {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername("clientUser");
        RoleEntity role = new RoleEntity();
        role.setTitle("CLIENT");
        userEntity.setRole(role);
        when(userRepository.findByUsernameAndDeletedFalse(anyString())).thenReturn(Optional.of(userEntity));

        UserEntity differentUserEntity = new UserEntity();
        differentUserEntity.setUsername("differentUser");
        BookingEntity bookingEntity = new BookingEntity();
        bookingEntity.setId(1L);
        bookingEntity.setUser(differentUserEntity);
        when(bookingRepository.findByIdAndDeletedFalse(anyLong())).thenReturn(Optional.of(bookingEntity));

        when(authentication.getName()).thenReturn("clientUser");
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        // Call the findBookingById method and expect an exception to be thrown
        assertThrows(GeneralException.class, () -> toTest.findBookingById(1L));
    }

    @Test
    public void test_findBookingByUserId_ok() {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername("clientUser");

        RoleEntity role = new RoleEntity();
        role.setTitle("CLIENT");
        userEntity.setRole(role);

        when(userRepository.findByIdAndDeletedFalse(anyLong())).thenReturn(Optional.of(userEntity));
        when(userRepository.findByUsernameAndDeletedFalse(anyString())).thenReturn(Optional.of(userEntity));

        BookingEntity bookingEntity = new BookingEntity();
        bookingEntity.setId(1L);
        bookingEntity.setUser(userEntity);

        List<BookingEntity> bookings = new ArrayList<>();
        bookings.add(bookingEntity);

        when(bookingRepository.findBookingsByUserAndDeletedFalse(any())).thenReturn(bookings);

        when(authentication.getName()).thenReturn("clientUser");
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        List<BookingDTO> result = toTest.findBookingsByUserId(1L);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(userEntity.getId(), result.get(0).getUser().getId());
    }


}