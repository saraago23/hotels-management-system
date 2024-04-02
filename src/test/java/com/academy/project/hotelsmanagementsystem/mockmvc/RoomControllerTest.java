package com.academy.project.hotelsmanagementsystem.mockmvc;

import com.academy.project.hotelsmanagementsystem.dto.HotelDTO;
import com.academy.project.hotelsmanagementsystem.dto.RoomDTO;
import com.academy.project.hotelsmanagementsystem.dto.RoomTypeDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc
@SpringBootTest
public class RoomControllerTest {

    @Autowired
    MockMvc mockMvc;

    ObjectMapper mapper= new ObjectMapper();
    private static RoomTypeDTO roomType;
    private static HotelDTO hotel;
    @BeforeAll
    static void setUp() {
        roomType = RoomTypeDTO.builder()
                .id(2L)
                .type("Deluxe-Double")
                .roomDesc("king sized bed")
                .price(BigDecimal.valueOf(60))
                .build();

        hotel=HotelDTO.builder()
                .id(1L)
                .hotelName("Dim's")
                .roomsNr(5)
                .address("Lgj.1")
                .phone("0686012366")
                .country("Albania")
                .postCode(7001)
                .city("Korce")
                .starRating(BigDecimal.valueOf(5))
                .build();
    }


    //@Test
    public void test_find_all() throws Exception {
        this.mockMvc.perform(get("/rooms"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(greaterThanOrEqualTo(3))))
                .andExpect(jsonPath("$.content[0].id").value(1))
                .andExpect(jsonPath("$.content[1].id").value(2))
                .andExpect(jsonPath("$.content[2].id").value(3));
    }

    //@Test
    public void test_find_all_2() throws Exception {
        this.mockMvc.perform(get("/rooms?page={page}&size={size}", 0, 3))
                .andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.content", hasSize(3)))
                .andExpect(jsonPath("$.content[0].id").value(1))
                .andExpect(jsonPath("$.content[1].id").value(2))
                .andExpect(jsonPath("$.content[2].id").value(3));
    }

    //@Test
    public void test_find_by_room_id() throws Exception {
        this.mockMvc.perform(get("/rooms/{id}", 1))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.roomType.id").value(1))
                .andExpect(jsonPath("$.hotel.id").value(1))
                .andExpect(jsonPath("$.booked").value(true));
    }


    //@Test
//    @Transactional
    public void test_add_room() throws Exception {
        var requestBody = RoomDTO.builder()
                .roomType(roomType)
                .hotel(hotel)
                .build();

        this.mockMvc.perform(post("/rooms")
                        .content(mapper.writeValueAsString(requestBody))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.roomType.id").value(2))
                .andExpect(jsonPath("$.hotel.id").value(1))
                .andExpect(jsonPath("$.booked").value(false));

    }

    //@Test
    @Transactional
    public void test_update_roomType()throws Exception{
        var requestBody = RoomDTO.builder()
                .roomType(roomType)
                .hotel(hotel)
                .build();

        this.mockMvc.perform(put("/rooms/{id}",1)
                .content(mapper.writeValueAsString(requestBody)).contentType(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.roomType.id").value(1))
                .andExpect(jsonPath("$.hotel.id").value(1))
                .andExpect(jsonPath("$.isBooked").value(false));

    }


}
