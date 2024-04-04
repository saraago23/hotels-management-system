package com.academy.project.hotelsmanagementsystem.controller;

import com.academy.project.hotelsmanagementsystem.BaseTest;
import com.academy.project.hotelsmanagementsystem.dto.BookingDTO;
import com.academy.project.hotelsmanagementsystem.dto.PageDTO;
import com.academy.project.hotelsmanagementsystem.dto.UserDTO;
import com.academy.project.hotelsmanagementsystem.service.BookingService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class BookingControllerTest extends BaseTest {

    @MockBean
    private BookingService bookingService;

    @Test
    public void test_find_all() throws Exception {
        SecurityContextHolder.getContext().setAuthentication(getAuthentication("ROLE_ADMIN"));
        List<BookingDTO> bookingsDTOList = new ArrayList<>();
        PageDTO<BookingDTO> bookings = new PageDTO<>(bookingsDTOList, 0L, 0, 0, true, false);
        doReturn(bookings).when(bookingService).findAll(Pageable.unpaged());
        mockMvc.perform(MockMvcRequestBuilders.get("/bookings"))
                .andExpect(status().isOk());
    }

    @Test
    public void test_find_booking_by_id() throws Exception {
        SecurityContextHolder.getContext().setAuthentication(getAuthentication("ROLE_CLIENT"));
        BookingDTO bookingDTO = new BookingDTO();
        doReturn(bookingDTO).when(bookingService).findBookingById(any());
        mockMvc.perform(MockMvcRequestBuilders.get("/bookings/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void test_addBooking_ok() throws Exception{
        SecurityContextHolder.getContext().setAuthentication(getAuthentication("ROLE_ADMIN"));
        var user = new UserDTO();
        user.setId(1L);
        user.setUsername("sara");
        user.setPassword("124567");

        BookingDTO bookingDTO= BookingDTO.builder()
                .id(1L)
                .user(user)
                .bookingTime(LocalDateTime.now())
                .checkInTime(LocalDateTime.now())
                .checkOutTime(LocalDateTime.now().plusDays(1))
                .specialReq("req")
                .totalPrice(BigDecimal.valueOf(100))
                .deleted(false)
                .build();
        doReturn(bookingDTO).when(bookingService).addBooking(any());
        mockMvc.perform(MockMvcRequestBuilders.post("/bookings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new BookingDTO())))
                .andExpect(status().isOk());
    }

    @Test
    public void test_updateBooking_ok() throws Exception{
        SecurityContextHolder.getContext().setAuthentication(getAuthentication("ROLE_ADMIN"));
        var user = new UserDTO();
        user.setId(1L);
        user.setUsername("sara");
        user.setPassword("124567");

        BookingDTO bookingDTO= BookingDTO.builder()
                .id(1L)
                .user(user)
                .bookingTime(LocalDateTime.now())
                .checkInTime(LocalDateTime.now())
                .checkOutTime(LocalDateTime.now().plusDays(1))
                .specialReq("req")
                .totalPrice(BigDecimal.valueOf(100))
                .deleted(false)
                .build();

        doReturn(bookingDTO).when(bookingService).addBooking(any());
        mockMvc.perform(MockMvcRequestBuilders.post("/bookings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new BookingDTO())))
                .andExpect(status().isOk());
    }

    @Test
    public void test_deleteBooking_ok() throws Exception{
        SecurityContextHolder.getContext().setAuthentication(getAuthentication("ROLE_ADMIN"));
        doNothing().when(bookingService).deleteBooking(anyLong());
        mockMvc.perform(MockMvcRequestBuilders.delete("/bookings/1"))
                .andExpect(status().isOk());
    }

}
