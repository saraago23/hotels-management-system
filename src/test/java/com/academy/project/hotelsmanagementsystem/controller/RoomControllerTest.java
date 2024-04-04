package com.academy.project.hotelsmanagementsystem.controller;

import com.academy.project.hotelsmanagementsystem.BaseTest;
import com.academy.project.hotelsmanagementsystem.dto.*;
import com.academy.project.hotelsmanagementsystem.service.RoomService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class RoomControllerTest extends BaseTest {

    @MockBean
    private RoomService roomService;

    @Test
    public void test_find_all() throws Exception {
        SecurityContextHolder.getContext().setAuthentication(getAuthentication("ROLE_ADMIN"));
        List<RoomDTO> roomDTOList = new ArrayList<>();
        PageDTO<RoomDTO> rooms = new PageDTO<>(roomDTOList, 0L, 0, 0, true, false);
        doReturn(rooms).when(roomService).findAll(Pageable.unpaged());
        mockMvc.perform(MockMvcRequestBuilders.get("/rooms"))
                .andExpect(status().isOk());
    }

    @Test
    public void test_find_room_by_id() throws Exception {
        SecurityContextHolder.getContext().setAuthentication(getAuthentication("ROLE_ADMIN"));
        RoomDTO roomDTO = new RoomDTO();
        doReturn(roomDTO).when(roomService).findRoomById(any());
        mockMvc.perform(MockMvcRequestBuilders.get("/rooms/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void test_addRoom_ok() throws Exception{
        SecurityContextHolder.getContext().setAuthentication(getAuthentication("ROLE_ADMIN"));
        RoomDTO roomDTO= RoomDTO.builder()
                .roomNr(3)
                .roomType(new RoomTypeDTO())
                .hotel(new HotelDTO())
                .deleted(false)
                .build();
        doReturn(roomDTO).when(roomService).addRoom(any());
        mockMvc.perform(MockMvcRequestBuilders.post("/rooms")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new RoomDTO())))
                .andExpect(status().isOk());
    }

    @Test
    public void test_updateRoom_ok() throws Exception{
        SecurityContextHolder.getContext().setAuthentication(getAuthentication("ROLE_ADMIN"));

        RoomDTO roomDTO= RoomDTO.builder()
                .roomNr(3)
                .roomType(new RoomTypeDTO())
                .hotel(new HotelDTO())
                .deleted(false)
                .build();
        doReturn(roomDTO).when(roomService).addRoom(any());
        mockMvc.perform(MockMvcRequestBuilders.post("/rooms")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new RoomDTO())))
                .andExpect(status().isOk());
    }

    @Test
    public void test_deleteCategory_ok() throws Exception{
        SecurityContextHolder.getContext().setAuthentication(getAuthentication("ROLE_ADMIN"));
        doNothing().when(roomService).deleteRoom(anyLong());
        mockMvc.perform(MockMvcRequestBuilders.delete("/rooms/1"))
                .andExpect(status().isOk());
    }

}
