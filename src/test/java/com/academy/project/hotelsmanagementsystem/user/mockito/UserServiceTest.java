package com.academy.project.hotelsmanagementsystem.user.mockito;

import com.academy.project.hotelsmanagementsystem.dto.UserDTO;
import com.academy.project.hotelsmanagementsystem.entity.RoleEntity;
import com.academy.project.hotelsmanagementsystem.entity.UserEntity;
import com.academy.project.hotelsmanagementsystem.repository.UserRepository;
import com.academy.project.hotelsmanagementsystem.service.UserService;
import com.academy.project.hotelsmanagementsystem.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static com.academy.project.hotelsmanagementsystem.mapper.RoleMapper.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Spy
    @InjectMocks
    UserService toTest = new UserServiceImpl();

    @Mock
    UserRepository userRepository;
    private static RoleEntity role;

    @BeforeAll
    static void setUp() {

        role = RoleEntity
                .builder()
                .id(1L)
                .build();
    }

    @BeforeEach
    void init() {
        reset(userRepository);
    }

    @Test
    public void test_findAll_ok() {
        var users = Arrays.asList(UserEntity.builder()
                .id(1L)
                .role(role)
                .build());

        Page<UserEntity> pageEntity = new PageImpl<>(users);
        doReturn(pageEntity).when(userRepository).findAll(any(Pageable.class));
        var output = toTest.findAll(PageRequest.of(0, 10));
        assertAll(
                () -> assertNotNull(output),
                () -> assertEquals(1, output.getContent().size()),
                () -> assertEquals(1L, output.getContent().get(0).getId())
        );
    }

    @Test
    public void test_addUser() {
        var mockUser = UserEntity.builder()
                .id(1L)
                .username("saraago")
                .password("12345678")
                .role(role)
                .build();

        doReturn(mockUser).when(userRepository).save(any());

        var input = UserDTO.builder().username("saraago")
                .password("12345678")
                .build();

        var output = toTest.addUser(input);
        assertAll(
                () -> assertNotNull(output),
                () -> assertEquals(1, output.getId()),
                () -> assertEquals("saraago", output.getUsername()),
                () -> assertEquals("12345678", output.getPassword())
        );
    }

    @Test
    public void test_updateUser_ok() {

        var mockUser = mock(UserEntity.class);
        doReturn(Optional.of(mockUser)).when(userRepository).findById(any());

        var savedEntity = UserEntity.builder()
                .id(1L)
                .username("saraago")
                .password("12345")
                .role(role)
                .build();

        doReturn(savedEntity).when(userRepository).save(any());

        var input = UserDTO.builder()
                .id(1L)
                .username("saraago")
                .password("12345")
                .role(ROLE_MAPPER.toDto(role))
                .build();

        var output = toTest.updateUser(1L, input);
        assertAll(
                () -> assertNotNull(output.getId()),
                () -> assertEquals("saraago", output.getUsername()),
                () -> assertEquals("12345", output.getPassword()),
                () -> assertEquals(ROLE_MAPPER.toDto(role), output.getRole())
        );
    }


}
