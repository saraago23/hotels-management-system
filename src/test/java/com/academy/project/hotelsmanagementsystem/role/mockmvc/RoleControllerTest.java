package com.academy.project.hotelsmanagementsystem.role.mockmvc;

import com.academy.project.hotelsmanagementsystem.dto.RoleDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc
@SpringBootTest
public class RoleControllerTest {
    @Autowired
    protected WebApplicationContext webApplicationContext;

    @Autowired
    MockMvc mockMvc;

    ObjectMapper mapper;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(springSecurity()).build();
        mapper = new ObjectMapper();
    }


    @Test
    public void test_get_non_deleted_roles() throws Exception {
        this.mockMvc.perform(get("/roles"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(greaterThanOrEqualTo(2))))
                .andExpect(jsonPath("$.content[0].id").value(1))
                .andExpect(jsonPath("$.content[1].id").value(2));
    }

    @Test
    public void test_find_all_2() throws Exception {
        this.mockMvc.perform(get("/roles?page={page}&size={size}", 0, 2))
                .andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.content", hasSize(2)))
                .andExpect(jsonPath("$.content[0].id").value(1))
                .andExpect(jsonPath("$.content[1].id").value(2));
    }

    @Test
    public void test_find_by_role_id() throws Exception {
        this.mockMvc.perform(get("/roles/{id}", 1))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("ADMIN"))
                .andExpect(jsonPath("$.description").value("administrator"));
    }


    @Test
//    @Transactional
    public void test_add_role() throws Exception {
        var requestBody = RoleDTO.builder()
                .title("Client")
                .description("books")
                .build();

        this.mockMvc.perform(post("/roles")
                        .content(mapper.writeValueAsString(requestBody))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.title").value("Client"))
                .andExpect(jsonPath("$.description").value("books"));

    }

    @Test
    @Transactional
    public void test_update_role() throws Exception {
        var requestBody = RoleDTO.builder()
                .title("Client")
                .description("books")
                .build();

        this.mockMvc.perform(put("/roles/{id}", 1)
                        .content(mapper.writeValueAsString(requestBody)).contentType(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Client"))
                .andExpect(jsonPath("$.description").value("books"));

    }


}
