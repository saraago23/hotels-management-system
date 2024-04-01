package com.academy.project.hotelsmanagementsystem.controller;

import com.academy.project.hotelsmanagementsystem.BaseTest;
import com.academy.project.hotelsmanagementsystem.dto.RoleDTO;
import com.academy.project.hotelsmanagementsystem.service.RoleService;
import org.junit.jupiter.api.Test;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

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
        List<RoleDTO> roles = new ArrayList<>();
        doReturn(roles).when(roleService).findAllNonDeleted(Pageable.unpaged());
        mockMvc.perform(MockMvcRequestBuilders.get("/roles"))
                .andExpect(status().isOk());
    }

   /* @Test
    public void test_find_all_2() throws Exception {
        this.mockMvc.perform(get("/api/offices?page={page}&size={size}",0,2))
                .andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.content",hasSize(2)))
                .andExpect(jsonPath("$.content[0].officeCode").value(1))
                .andExpect(jsonPath("$.content[1].officeCode").value(2));
    }

    @Test
    public void test_find_by_office_code() throws Exception {
        this.mockMvc.perform(get("/api/offices/{officeCode}",1))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.officeCode").value(1))
                .andExpect(jsonPath("$.city").value("San Francisco"))
                .andExpect(jsonPath("$.phone").value("+1 650 219 4782"))
                .andExpect(jsonPath("$.addressLine1").value("100 Market Street"))
                .andExpect(jsonPath("$.addressLine2").value("Suite 300"))
                .andExpect(jsonPath("$.state").value("CA"))
                .andExpect(jsonPath("$.country").value("USA"))
                .andExpect(jsonPath("$.postalCode").value("94080"))
                .andExpect(jsonPath("$.territory").value("NA"));
    }


    @Test
//    @Transactional
    public void test_add_office() throws Exception {
        var requestBody = OfficeDTO.builder().addressLine1("address").phone("+355").city("city")
                .country("country").postalCode("11111").territory("territory").build();

        this.mockMvc.perform(post("/api/offices")
                        .content(mapper.writeValueAsString(requestBody))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.officeCode",notNullValue()))
                .andExpect(jsonPath("$.city").value("city"))
                .andExpect(jsonPath("$.addressLine1").value("address"))
                .andExpect(jsonPath("$.phone").value("+355"))
                .andExpect(jsonPath("$.country").value("country"))
                .andExpect(jsonPath("$.postalCode").value("11111"))
                .andExpect(jsonPath("$.territory").value("territory"));

    }

    @Test
    public void test_add_office_ko() throws Exception {
        var officeRequest = OfficeDTO.builder().build();
        this.mockMvc.perform(post("/api/offices").content(mapper.writeValueAsString(officeRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().is4xxClientError());
    }

    @Test
    @Transactional
    public void test_update_office()throws Exception{
        var requestBody = OfficeDTO.builder().phone("+1 650 219 4782").city("San Francisco 111")
                .country("USA").state("CA").postalCode("94080").territory("NA")
                .addressLine1("100 Market Street").addressLine2("Suite 300").build();

        this.mockMvc.perform(put("/api/offices/{officeCode}",1)
                .content(mapper.writeValueAsString(requestBody)).contentType(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.officeCode").value(1))
                .andExpect(jsonPath("$.city").value("San Francisco 111"))
                .andExpect(jsonPath("$.phone").value("+1 650 219 4782"))
                .andExpect(jsonPath("$.addressLine1").value("100 Market Street"))
                .andExpect(jsonPath("$.addressLine2").value("Suite 300"))
                .andExpect(jsonPath("$.state").value("CA"))
                .andExpect(jsonPath("$.country").value("USA"))
                .andExpect(jsonPath("$.postalCode").value("94080"))
                .andExpect(jsonPath("$.territory").value("NA"));

    }

*/


}
