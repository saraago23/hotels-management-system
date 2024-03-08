package com.academy.project.hotelsmanagementsystem.roomtype;

import com.academy.project.hotelsmanagementsystem.dto.RoomTypeDTO;
import com.academy.project.hotelsmanagementsystem.entity.RoomTypeEntity;
import com.academy.project.hotelsmanagementsystem.repository.RoomTypeRepository;
import com.academy.project.hotelsmanagementsystem.service.RoomTypeService;
import com.academy.project.hotelsmanagementsystem.service.impl.RoomTypeServiceImpl;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RoomTypeServiceTest {

    @Spy
    @InjectMocks
    RoomTypeService toTest = new RoomTypeServiceImpl();

    @Mock
    RoomTypeRepository roomTypeRepository;

    @BeforeEach
    void init() {
        reset(roomTypeRepository);
    }

    @Test
    public void test_findAll_ok() {
        var roomTypes = Arrays.asList(RoomTypeEntity.builder()
                .id(1L)
                        .roomDesc("two single beds")
                        .type("Twin-Room")
                        .price(50)
                .build());

        Page<RoomTypeEntity> pageEntity = new PageImpl<>(roomTypes);
        doReturn(pageEntity).when(roomTypeRepository).findAll(any(Pageable.class));
        var output = toTest.findAll(PageRequest.of(0, 10));
        assertAll(
                () -> assertNotNull(output),
                () -> assertEquals(1, output.getContent().size()),
                () -> assertEquals(1L, output.getContent().get(0).getId())
        );
    }

    @Test
    public void test_addRoomType() {
        var mockRoomType = RoomTypeEntity.builder()
                .id(1L)
                .roomDesc("two single beds")
                .type("Twin-Room")
                .price(50)
                .build();

        doReturn(mockRoomType).when(roomTypeRepository).save(any());

        var input = RoomTypeDTO.builder()
                .roomDesc("two single beds")
                .type("Twin-Room")
                .price(50)
                .build();

        var output = toTest.addRoomType(input);
        assertAll(
                () -> assertNotNull(output),
                () -> assertEquals(1, output.getId()),
                () -> assertEquals("two single beds", output.getRoomDesc()),
                () -> assertEquals("Twin-Room", output.getType()),
                () -> assertEquals(50, output.getPrice())
        );
    }

    @Test
    public void test_updateRoomType_ok() {

        var mockRoomType = mock(RoomTypeEntity.class);
        doReturn(Optional.of(mockRoomType)).when(roomTypeRepository).findById(any());

        var savedEntity = RoomTypeEntity.builder()
                .id(1L)
                .roomDesc("two single beds")
                .type("Twin-Room")
                .price(50)
                .build();

        doReturn(savedEntity).when(roomTypeRepository).save(any());

        var input = RoomTypeDTO.builder()
                .id(1L)
                .roomDesc("two single beds")
                .type("Twin-Room")
                .price(50)
                .build();

        var output = toTest.updateRoomType(1L, input);
        assertAll(
                () -> assertNotNull(output.getId()),
                () -> assertEquals("two single beds", output.getRoomDesc()),
                () -> assertEquals("Twin-Room", output.getType()),
                () -> assertEquals(50, output.getPrice())

        );
    }


}
