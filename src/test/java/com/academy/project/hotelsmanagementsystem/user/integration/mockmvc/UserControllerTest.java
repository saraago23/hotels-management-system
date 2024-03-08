package com.academy.project.hotelsmanagementsystem.user.integration.mockmvc;

import com.academy.project.hotelsmanagementsystem.dto.RoleDTO;
import com.academy.project.hotelsmanagementsystem.dto.UserDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc
@SpringBootTest
public class UserControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;
    private static RoleDTO role;

    @BeforeAll
    static void setUp() {
        role = RoleDTO.builder()
                .id(1L)
                .title("ADMIN")
                .description("Administrates")
                .build();
    }

    @BeforeEach
    void setUpMapper(){
        mapper.registerModule(new JavaTimeModule());
    }

    @Test
    public void test_find_all() throws Exception {
        this.mockMvc.perform(get("/users"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(greaterThanOrEqualTo(3))))
                .andExpect(jsonPath("$.content[0].id").value(1))
                .andExpect(jsonPath("$.content[1].id").value(2))
                .andExpect(jsonPath("$.content[2].id").value(3));

    }

    @Test
    public void test_find_all_2() throws Exception {
        this.mockMvc.perform(get("/users?page={page}&size={size}", 0, 3))
                .andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.content", hasSize(3)))
                .andExpect(jsonPath("$.content[0].id").value(1))
                .andExpect(jsonPath("$.content[1].id").value(2))
                .andExpect(jsonPath("$.content[2].id").value(3));
    }

    @Test
    public void test_find_by_user_id() throws Exception {
        this.mockMvc.perform(get("/users/{id}", 1))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.role.id").value(1))
                .andExpect(jsonPath("$.firstName").value("Sara"))
                .andExpect(jsonPath("$.lastName").value("Ago"))
                .andExpect(jsonPath("$.birthDate").value("2000-06-23"))
                .andExpect(jsonPath("$.gender").value("F"))
                .andExpect(jsonPath("$.phone").value("0688032311"))
                .andExpect(jsonPath("$.email").value("saraago@hotmail.com"))
                .andExpect(jsonPath("$.address").value("Lgj.18"))
                .andExpect(jsonPath("$.username").value("saraago23"))
                .andExpect(jsonPath("$.password").value("lalala"));
    }


    @Test
//    @Transactional
    public void test_add_office() throws Exception {
        var requestBody = UserDTO.builder()
                .role(role)
                .firstName("first")
                .lastName("last")
                .birthDate(LocalDate.of(2000, 3, 4))
                .gender('M')
                .phone("0693452133")
                .email("anon@gmail.com")
                .address("address")
                .username("anon")
                .password("12345678")
                .build();

        this.mockMvc.perform(post("/users")
                        .content(mapper.writeValueAsString(requestBody))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.role.id").value(1))
                .andExpect(jsonPath("$.firstName").value("first"))
                .andExpect(jsonPath("$.lastName").value("last"))
                .andExpect(jsonPath("$.birthDate").value("2000-03-04"))
                .andExpect(jsonPath("$.gender").value("M"))
                .andExpect(jsonPath("$.phone").value("0693452133"))
                .andExpect(jsonPath("$.email").value("anon@gmail.com"))
                .andExpect(jsonPath("$.address").value("address"))
                .andExpect(jsonPath("$.username").value("anon"))
                .andExpect(jsonPath("$.password").value("12345678"));

    }

    @Test
    @Transactional
    public void test_update_user()throws Exception{
        var requestBody = UserDTO.builder()
                .role(role)
                .firstName("sara")
                .lastName("ago")
                .birthDate(LocalDate.of(2000, 6, 23))
                .gender('F')
                .phone("0688032311")
                .email("saraago@hotmail.com")
                .address("Lgj.18")
                .username("saraago23")
                .password("lalala")
                .build();

        this.mockMvc.perform(put("/users/{id}",1)
                .content(mapper.writeValueAsString(requestBody)).contentType(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.role.id").value(1))
                .andExpect(jsonPath("$.firstName").value("sara"))
                .andExpect(jsonPath("$.lastName").value("ago"))
                .andExpect(jsonPath("$.birthDate").value("2000-06-23"))
                .andExpect(jsonPath("$.gender").value("F"))
                .andExpect(jsonPath("$.phone").value("0688032311"))
                .andExpect(jsonPath("$.email").value("saraago@hotmail.com"))
                .andExpect(jsonPath("$.address").value("Lgj.18"))
                .andExpect(jsonPath("$.username").value("saraago23"))
                .andExpect(jsonPath("$.password").value("lalala"));

    }


}
