package com.academy.project.hotelsmanagementsystem.controller;

import com.academy.project.hotelsmanagementsystem.BaseTest;
import com.academy.project.hotelsmanagementsystem.dto.PageDTO;
import com.academy.project.hotelsmanagementsystem.dto.RoleDTO;
import com.academy.project.hotelsmanagementsystem.service.RoleService;
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
public class RoleControllerTest extends BaseTest {

    @MockBean
    private RoleService roleService;

    @Test
    public void test_find_all() throws Exception {
        SecurityContextHolder.getContext().setAuthentication(getAuthentication("ROLE_ADMIN"));
        List<RoleDTO> roleDTOList = new ArrayList<>();
        PageDTO<RoleDTO> roles = new PageDTO<>(roleDTOList, 0L, 0, 0, true, false);
        doReturn(roles).when(roleService).findAllNonDeleted(Pageable.unpaged());
        mockMvc.perform(MockMvcRequestBuilders.get("/roles"))
                .andExpect(status().isOk());
    }

    @Test
    public void test_find_role_by_id() throws Exception {
        SecurityContextHolder.getContext().setAuthentication(getAuthentication("ROLE_ADMIN"));
        RoleDTO roleDTO = new RoleDTO();
        doReturn(roleDTO).when(roleService).findRoleById(any());
        mockMvc.perform(MockMvcRequestBuilders.get("/roles/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void test_addRole_ok() throws Exception{
        SecurityContextHolder.getContext().setAuthentication(getAuthentication("ROLE_ADMIN"));
        RoleDTO roleDTO= RoleDTO.builder()
                .title("title")
                .description("desc")
                .deleted(false)
                .build();
        doReturn(roleDTO).when(roleService).addRole(any());
        mockMvc.perform(MockMvcRequestBuilders.post("/roles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new RoleDTO())))
                .andExpect(status().isOk());
    }

    @Test
    public void test_updateRole_ok() throws Exception{
        SecurityContextHolder.getContext().setAuthentication(getAuthentication("ROLE_ADMIN"));

        RoleDTO roleDTO= RoleDTO.builder()
                .title("title")
                .description("desc")
                .deleted(false)
                .build();
        doReturn(roleDTO).when(roleService).addRole(any());
        mockMvc.perform(MockMvcRequestBuilders.post("/roles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new RoleDTO())))
                .andExpect(status().isOk());
    }

    @Test
    public void test_deleteCategory_ok() throws Exception{
        SecurityContextHolder.getContext().setAuthentication(getAuthentication("ROLE_ADMIN"));
        doNothing().when(roleService).deleteRole(anyLong());
        mockMvc.perform(MockMvcRequestBuilders.delete("/roles/1"))
                .andExpect(status().isOk());
    }

}
