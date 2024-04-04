package com.academy.project.hotelsmanagementsystem.service;

import com.academy.project.hotelsmanagementsystem.dto.RoomTypeDTO;
import com.academy.project.hotelsmanagementsystem.entity.RoomTypeEntity;
import com.academy.project.hotelsmanagementsystem.repository.RoomTypeRepository;
import com.academy.project.hotelsmanagementsystem.service.impl.RoomTypeServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class RoomTypeServiceTest {
    @Spy
    @InjectMocks
    RoomTypeService toTest = new RoomTypeServiceImpl();

    @Mock
    RoomTypeRepository roomTypeRepository;

    @Test
    public void test_findAll_ok() {

        var roomTypes = Arrays.asList(RoomTypeEntity.builder()
                .id(1L)
                .type("Deluxe-Double")
                .price(BigDecimal.valueOf(60))
                .numGuest(3)
                .roomDesc("none")
                .deleted(false)
                .build());

        Page<RoomTypeEntity> roomTypeEntities = new PageImpl<>(roomTypes);
        doReturn(roomTypeEntities).when(roomTypeRepository).findAllNonDeleted(any(Pageable.class));
        var output = toTest.findAll(Pageable.unpaged());
        assertAll(
                () -> assertNotNull(output),
                () -> assertEquals(1, output.getContent().size()),
                () -> assertEquals(1L, output.getContent().get(0).getId())
        );
    }

    @Test
    public void test_findRoomTypeById_ok() {

        var mockRoomType = RoomTypeEntity.builder()
                .id(1L)
                .type("Deluxe-Double")
                .price(BigDecimal.valueOf(60))
                .numGuest(3)
                .roomDesc("none")
                .deleted(false)
                .build();

        doReturn(Optional.of(mockRoomType)).when(roomTypeRepository).findByIdAndDeletedFalse(any());

        var output = toTest.findRoomTypeById(1L);
        assertAll(
                () -> assertNotNull(output),
                () -> assertEquals(1L, output.getId()),
                () -> assertEquals("Deluxe-Double", output.getType()),
                () -> assertEquals(BigDecimal.valueOf(60), output.getPrice()),
                () -> assertEquals(3, output.getNumGuest()),
                () -> assertEquals("none", output.getRoomDesc())
        );
    }

    @Test
    public void test_addRoomType() {
        var mockRoomType = RoomTypeEntity.builder()
                .id(1L)
                .type("Deluxe-Double")
                .price(BigDecimal.valueOf(60))
                .numGuest(3)
                .roomDesc("none")
                .deleted(false)
                .build();

        doReturn(mockRoomType).when(roomTypeRepository).save(any());

        var mockRoomTypeDto = RoomTypeDTO.builder()
                .id(1L)
                .type("Deluxe-Double")
                .price(BigDecimal.valueOf(60))
                .numGuest(3)
                .roomDesc("none")
                .deleted(false)
                .build();

        var output = toTest.addRoomType(mockRoomTypeDto);
        assertAll(
                () -> assertNotNull(output),
                () -> assertEquals(1L, output.getId()),
                () -> assertEquals("Deluxe-Double", output.getType()),
                () -> assertEquals(BigDecimal.valueOf(60), output.getPrice()),
                () -> assertEquals(3, output.getNumGuest()),
                () -> assertEquals("none", output.getRoomDesc())
        );
    }

    @Test
    public void test_updateRoomType_ok() {

        var mockRoomType = mock(RoomTypeEntity.class);
        doReturn(Optional.of(mockRoomType)).when(roomTypeRepository).findByIdAndDeletedFalse(any());

        var mockRoomType1 = RoomTypeEntity.builder()
                .id(1L)
                .type("Deluxe-Double")
                .price(BigDecimal.valueOf(60))
                .numGuest(3)
                .roomDesc("none")
                .deleted(false)
                .build();

        doReturn(mockRoomType1).when(roomTypeRepository).save(any());

        var mockRoomTypeDto = RoomTypeDTO.builder()
                .id(1L)
                .type("Deluxe-Double")
                .price(BigDecimal.valueOf(60))
                .numGuest(3)
                .roomDesc("none")
                .deleted(false)
                .build();

        var output = toTest.updateRoomType(1L, mockRoomTypeDto);
        assertAll(
                () -> assertNotNull(output),
                () -> assertEquals(1L, output.getId()),
                () -> assertEquals("Deluxe-Double", output.getType()),
                () -> assertEquals(BigDecimal.valueOf(60), output.getPrice()),
                () -> assertEquals(3, output.getNumGuest()),
                () -> assertEquals("none", output.getRoomDesc())
        );
    }
    @Test
    public void test_deleteRoomType_ok() {

        var mockRoomType1 = RoomTypeEntity.builder()
                .id(1L)
                .type("Deluxe-Double")
                .price(BigDecimal.valueOf(60))
                .numGuest(3)
                .roomDesc("none")
                .deleted(false)
                .build();

        doReturn(Optional.of(mockRoomType1)).when(roomTypeRepository).findByIdAndDeletedFalse(any());

        toTest.deleteRoomType(1L);
    }
}
