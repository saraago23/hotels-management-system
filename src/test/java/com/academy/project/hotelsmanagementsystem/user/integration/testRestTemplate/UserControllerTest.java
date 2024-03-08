package com.academy.project.hotelsmanagementsystem.user.integration.testRestTemplate;

import com.academy.project.hotelsmanagementsystem.dto.PageDTO;
import com.academy.project.hotelsmanagementsystem.dto.UserDTO;
import com.academy.project.hotelsmanagementsystem.entity.RoleEntity;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.time.LocalDate;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static com.academy.project.hotelsmanagementsystem.mapper.RoleMapper.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerTest {

    @Autowired
    TestRestTemplate restTemplate;
    private static RoleEntity role;

    @BeforeAll
    static void setUp() {

        role = RoleEntity
                .builder()
                .id(1L)
                .build();
    }

    @Test
    public void test_find_all_users_ok() {
        var response = this.restTemplate
                .exchange("/users", HttpMethod.GET, new HttpEntity<>(null), PageDTO.class);
        var body = Objects.requireNonNull(response.getBody()).getContent();
        assertAll(
                () -> assertTrue(response.getStatusCode().is2xxSuccessful()),
                () -> assertFalse(body.isEmpty()),
                () -> assertTrue(() -> body.size() > 4)
        );
    }

    @Test
    public void test_find_all_max_size_2() {
        var response = this.restTemplate.exchange("/users?page={page}&size={size}", HttpMethod.GET, new HttpEntity<>(null), PageDTO.class, 0, 5);
        var body = Objects.requireNonNull(response.getBody()).getContent();
        assertAll(
                () -> assertTrue(response.getStatusCode().is2xxSuccessful()),
                () -> assertFalse(body.isEmpty()),
                () -> assertEquals(5, body.size())
        );
    }

    @Test
    public void test_find_by_user_id() {
        var response = this.restTemplate.exchange("/users/{id}", HttpMethod.GET, new HttpEntity<>(null), UserDTO.class, 1L);
        assertTrue(response.getStatusCode().is2xxSuccessful());
    }


    @Test
    public void test_create_user() {
        var headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        var requestBody = UserDTO.builder()
                .username("saraago")
                .firstName("Sara")
                .lastName("Ago")
                .address("Lagjia 1")
                .birthDate(LocalDate.now())
                .email("saraago@yahoo.com")
                .password("12345")
                .role(ROLE_MAPPER.toDto(role))
                .build();

        var httpEntity = new HttpEntity<>(requestBody, headers);

        var response = this.restTemplate
                .exchange("/users", HttpMethod.POST, httpEntity, UserDTO.class);

        assertAll(
                () -> assertTrue(response.getStatusCode().is2xxSuccessful()),
                () -> assertNotNull(response.getBody().getId()),
                () -> assertEquals("saraago", response.getBody().getUsername()),
                () -> assertEquals("12345", response.getBody().getPassword()),
                () -> assertEquals(ROLE_MAPPER.toDto(role), response.getBody().getRole())
        );


    }
}
