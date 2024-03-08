package com.academy.project.hotelsmanagementsystem.roomtype.mockmvc;

import com.academy.project.hotelsmanagementsystem.dto.RoomTypeDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc
@SpringBootTest
public class RoomTypeControllerTest {

    @Autowired
    MockMvc mockMvc;

    ObjectMapper mapper= new ObjectMapper();


    @Test
    public void test_find_all() throws Exception {
        this.mockMvc.perform(get("/roomtypes"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(greaterThanOrEqualTo(3))))
                .andExpect(jsonPath("$.content[0].id").value(1))
                .andExpect(jsonPath("$.content[1].id").value(2))
                .andExpect(jsonPath("$.content[2].id").value(3));
    }

    @Test
    public void test_find_all_2() throws Exception {
        this.mockMvc.perform(get("/roomtypes?page={page}&size={size}", 0, 3))
                .andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.content", hasSize(3)))
                .andExpect(jsonPath("$.content[0].id").value(1))
                .andExpect(jsonPath("$.content[1].id").value(2))
                .andExpect(jsonPath("$.content[2].id").value(3));
    }

    @Test
    public void test_find_by_roomType_id() throws Exception {
        this.mockMvc.perform(get("/roomtypes/{id}", 1))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.type").value("Twin-Room"))
                .andExpect(jsonPath("$.price").value(50))
                .andExpect(jsonPath("$.roomDesc").value("two single beds"));
    }


    @Test
//    @Transactional
    public void test_add_roomType() throws Exception {
        var requestBody = RoomTypeDTO.builder()
                .type("Deluxe-Double")
                .roomDesc("king sized bed")
                .price(60)
                .build();

        this.mockMvc.perform(post("/roomtypes")
                        .content(mapper.writeValueAsString(requestBody))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.type").value("Deluxe-Double"))
                .andExpect(jsonPath("$.price").value(60))
                .andExpect(jsonPath("$.roomDesc").value("king sized bed"));

    }

    @Test
    @Transactional
    public void test_update_roomType()throws Exception{
        var requestBody = RoomTypeDTO.builder()
                .type("Suite")
                .roomDesc("exclusive suite")
                .price(110)
                .build();

        this.mockMvc.perform(put("/roomtypes/{id}",3)
                .content(mapper.writeValueAsString(requestBody)).contentType(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(3))
                .andExpect(jsonPath("$.type").value("Suite"))
                .andExpect(jsonPath("$.price").value(110))
                .andExpect(jsonPath("$.roomDesc").value("exclusive suite"));

    }


}
