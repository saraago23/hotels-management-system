package com.academy.project.hotelsmanagementsystem.controller;

import com.academy.project.hotelsmanagementsystem.BaseTest;
import com.academy.project.hotelsmanagementsystem.dto.*;
import com.academy.project.hotelsmanagementsystem.service.RoomBookedService;
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
public class RoomBookedControllerTest extends BaseTest {

    @MockBean
    private RoomBookedService roomBookedService;

    @Test
    public void test_find_all() throws Exception {
        SecurityContextHolder.getContext().setAuthentication(getAuthentication("ROLE_ADMIN"));
        List<RoomBookedDTO> roomBookedDTOListDTOList = new ArrayList<>();
        PageDTO<RoomBookedDTO> roomsBooked = new PageDTO<>(roomBookedDTOListDTOList, 0L, 0, 0, true, false);
        doReturn(roomsBooked).when(roomBookedService).findAll(Pageable.unpaged());
        mockMvc.perform(MockMvcRequestBuilders.get("/roomsbooked"))
                .andExpect(status().isOk());
    }

    @Test
    public void test_find_roomBooked_by_id() throws Exception {
        SecurityContextHolder.getContext().setAuthentication(getAuthentication("ROLE_ADMIN"));
        RoomBookedDTO roomDTO = new RoomBookedDTO();
        doReturn(roomDTO).when(roomBookedService).findRoomBookedById(any());
        mockMvc.perform(MockMvcRequestBuilders.get("/roomsbooked/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void test_add_roomBooked_ok() throws Exception{
        SecurityContextHolder.getContext().setAuthentication(getAuthentication("ROLE_ADMIN"));

        doReturn(new RoomBookedDTO()).when(roomBookedService).addRoomBooked(any());
        mockMvc.perform(MockMvcRequestBuilders.post("/roomsbooked")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new RoomBookedDTO())))
                .andExpect(status().isOk());
    }

    @Test
    public void test_update_roomBooked_ok() throws Exception{
        SecurityContextHolder.getContext().setAuthentication(getAuthentication("ROLE_ADMIN"));
        SecurityContextHolder.getContext().setAuthentication(getAuthentication("ROLE_ADMIN"));

        doReturn(new RoomBookedDTO()).when(roomBookedService).addRoomBooked(any());
        mockMvc.perform(MockMvcRequestBuilders.post("/roomsbooked")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new RoomBookedDTO())))
                .andExpect(status().isOk());
    }

    @Test
    public void test_deleteroomBooked_ok() throws Exception{
        SecurityContextHolder.getContext().setAuthentication(getAuthentication("ROLE_ADMIN"));
        doNothing().when(roomBookedService).deleteRoomBooked(anyLong());
        mockMvc.perform(MockMvcRequestBuilders.delete("/roomsbooked/1"))
                .andExpect(status().isOk());
    }

}
