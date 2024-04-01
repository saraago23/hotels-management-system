package com.academy.project.hotelsmanagementsystem.role;

import com.academy.project.hotelsmanagementsystem.dto.RoleDTO;
import com.academy.project.hotelsmanagementsystem.entity.RoleEntity;
import com.academy.project.hotelsmanagementsystem.repository.RoleRepository;
import com.academy.project.hotelsmanagementsystem.repository.UserRepository;
import com.academy.project.hotelsmanagementsystem.service.RoleService;
import com.academy.project.hotelsmanagementsystem.service.impl.RoleServiceImpl;
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
import static org.mockito.Mockito.doReturn;

/*
@ExtendWith(MockitoExtension.class)
public class RoleServiceTest {
    @Spy
    @InjectMocks
    RoleService toTest = new RoleServiceImpl();

    @Mock
    RoleRepository roleRepository;
    @Mock
    UserRepository userRepository;

    @Test
    public void test_findAll_ok() {
        var roles = Arrays.asList(RoleEntity.builder()
                .id(1L)
                .title("admin")
                .description("administrates")
                .build());

        Page<RoleEntity> pageEntity = new PageImpl<>(roles);
        doReturn(pageEntity).when(roleRepository).findAll(any(Pageable.class));
        var output = toTest.findAllNonDeleted(PageRequest.of(0, 10));
        assertAll(
                () -> assertNotNull(output),
                () -> assertEquals(1, output.getContent().size()),
                () -> assertEquals(1L, output.getContent().get(0).getId())
        );
    }

    @Test
    public void test_addRole() {
        var mockRole = RoleEntity.builder()
                .id(1L)
                .title("admin")
                .description("administrates")
                .build();

        doReturn(mockRole).when(roleRepository).save(any());

        var input = RoleDTO.builder()
                .title("admin")
                .description("administrates")
                .build();

        var output = toTest.addRole(input);
        assertAll(
                () -> assertNotNull(output),
                () -> assertEquals(1L, output.getId()),
                () -> assertEquals("admin", output.getTitle()),
                () -> assertEquals("administrates", output.getDescription())
        );
    }

    @Test
    public void test_updateUser_ok() {

        var mockRole = mock(RoleEntity.class);
        doReturn(Optional.of(mockRole)).when(roleRepository).findById(any());

        var savedEntity = RoleEntity.builder()
                .id(1L)
                .title("admin")
                .description("administrates")
                .build();

        doReturn(savedEntity).when(roleRepository).save(any());

        var input =RoleDTO.builder()
                .id(1L)
                .title("admin")
                .description("administrates")
                .build();

        var output = toTest.updateRole(1L, input);
        assertAll(
                () -> assertNotNull(output.getId()),
                () -> assertEquals("admin", output.getTitle()),
                () -> assertEquals("administrates", output.getDescription())
        );
    }
}
*/
