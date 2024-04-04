package com.academy.project.hotelsmanagementsystem.service;

import com.academy.project.hotelsmanagementsystem.dto.HotelDTO;
import com.academy.project.hotelsmanagementsystem.entity.BookingEntity;
import com.academy.project.hotelsmanagementsystem.entity.HotelEntity;
import com.academy.project.hotelsmanagementsystem.entity.RoomBookedEntity;
import com.academy.project.hotelsmanagementsystem.exceptions.GeneralException;
import com.academy.project.hotelsmanagementsystem.repository.HotelRepository;
import com.academy.project.hotelsmanagementsystem.repository.RoomBookedRepository;
import com.academy.project.hotelsmanagementsystem.service.impl.HotelServiceImpl;
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
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import static com.academy.project.hotelsmanagementsystem.mapper.HotelMapper.*;

@ExtendWith(MockitoExtension.class)
public class HotelServiceTest {
    @Spy
    @InjectMocks
    HotelService toTest = new HotelServiceImpl();
    @Mock
    HotelRepository hotelRepository;
    @Mock
    RoomBookedRepository roomBookedRepository;

    @Test
    public void test_findAll_ok() {

        var hotels = Arrays.asList(HotelEntity.builder()
                .id(1L)
                .hotelName("Dim's")
                .address("Lgj.1")
                .city("Korce")
                .country("Albania")
                .phone("0688032311")
                .roomsNr(6)
                .postCode(7001)
                .starRating(BigDecimal.valueOf(5))
                .deleted(false)
                .build());

        Page<HotelEntity> pageEntity = new PageImpl<>(hotels);
        doReturn(pageEntity).when(hotelRepository).findAllNonDeleted(any(Pageable.class));
        var output = toTest.findAll(Pageable.unpaged());
        assertAll(
                () -> assertNotNull(output),
                () -> assertEquals(1, output.getContent().size()),
                () -> assertEquals(1L, output.getContent().get(0).getId())
        );
    }

    @Test
    public void test_findHotelById_ok() {

        var mockHotel = HotelEntity.builder()
                .id(1L)
                .hotelName("Dim's")
                .address("Lgj.1")
                .city("Korce")
                .country("Albania")
                .phone("0688032311")
                .roomsNr(6)
                .postCode(7001)
                .starRating(BigDecimal.valueOf(5))
                .deleted(false)
                .build();

        doReturn(Optional.of(mockHotel)).when(hotelRepository).findByIdAndDeletedFalse(any());

        var output = toTest.findHotelById(1L);
        assertAll(
                () -> assertNotNull(output),
                () -> assertEquals(1L, output.getId()),
                () -> assertEquals("Dim's", output.getHotelName()),
                () -> assertEquals("Lgj.1", output.getAddress()),
                () -> assertEquals("Korce", output.getCity()),
                () -> assertEquals("Albania", output.getCountry()),
                () -> assertEquals("0688032311", output.getPhone()),
                () -> assertEquals(6, output.getRoomsNr()),
                () -> assertEquals(7001, output.getPostCode()),
                () -> assertEquals(BigDecimal.valueOf(5), output.getStarRating()),
                () -> assertEquals(false, output.getDeleted())
        );
    }

    @Test
    public void test_addHotel_ok() {
        var mockHotel = HotelEntity.builder()
                .id(1L)
                .hotelName("Dim's")
                .address("Lgj.1")
                .city("Korce")
                .country("Albania")
                .phone("0688032311")
                .roomsNr(6)
                .postCode(7001)
                .starRating(BigDecimal.valueOf(5))
                .deleted(false)
                .build();

        doReturn(mockHotel).when(hotelRepository).save(any());

        var mockHotelDto = HotelDTO.builder()
                .id(1L)
                .hotelName("Dim's")
                .address("Lgj.1")
                .city("Korce")
                .country("Albania")
                .phone("0688032311")
                .roomsNr(6)
                .postCode(7001)
                .starRating(BigDecimal.valueOf(5))
                .deleted(false)
                .build();

        var output = toTest.addHotel(mockHotelDto);
        assertAll(
                () -> assertNotNull(output),
                () -> assertEquals(1L, output.getId()),
                () -> assertEquals("Dim's", output.getHotelName()),
                () -> assertEquals("Lgj.1", output.getAddress()),
                () -> assertEquals("Korce", output.getCity()),
                () -> assertEquals("Albania", output.getCountry()),
                () -> assertEquals("0688032311", output.getPhone()),
                () -> assertEquals(6, output.getRoomsNr()),
                () -> assertEquals(7001, output.getPostCode()),
                () -> assertEquals(BigDecimal.valueOf(5), output.getStarRating()),
                () -> assertEquals(false, output.getDeleted())
        );
    }


    @Test
    public void test_updateHotel_ok() {
        var mockHotel = mock(HotelEntity.class);
        doReturn(Optional.of(mockHotel)).when(hotelRepository).findByIdAndDeletedFalse(any());

        var mockHotel1 = HotelEntity.builder()
                .id(1L)
                .hotelName("Dim's")
                .address("Lgj.1")
                .city("Korce")
                .country("Albania")
                .phone("0688032311")
                .roomsNr(6)
                .postCode(7001)
                .starRating(BigDecimal.valueOf(5))
                .deleted(false)
                .build();

        doReturn(mockHotel1).when(hotelRepository).save(any());

        var input = HotelDTO.builder()
                .id(1L)
                .hotelName("Dim's")
                .address("Lgj.1")
                .city("Korce")
                .country("Albania")
                .phone("0688032311")
                .roomsNr(6)
                .postCode(7001)
                .starRating(BigDecimal.valueOf(5))
                .deleted(false)
                .build();


        var output = toTest.updateHotel(1L, input);
        assertAll(
                () -> assertNotNull(output),
                () -> assertEquals(1L, output.getId()),
                () -> assertEquals("Dim's", output.getHotelName()),
                () -> assertEquals("Lgj.1", output.getAddress()),
                () -> assertEquals("Korce", output.getCity()),
                () -> assertEquals("Albania", output.getCountry()),
                () -> assertEquals("0688032311", output.getPhone()),
                () -> assertEquals(6, output.getRoomsNr()),
                () -> assertEquals(7001, output.getPostCode()),
                () -> assertEquals(BigDecimal.valueOf(5), output.getStarRating()),
                () -> assertEquals(false, output.getDeleted())
        );
    }

    @Test
    public void test_test_deleteHotel_noActiveBookings_ok() {

        Long hotelId = 1L;
        HotelEntity hotelEntity = new HotelEntity();
        hotelEntity.setId(hotelId);
        hotelEntity.setDeleted(false);

        when(hotelRepository.findByIdAndDeletedFalse(hotelId)).thenReturn(Optional.of(hotelEntity));
        when(roomBookedRepository.findRoomBookedEntitiesByHotelIdAndDeletedFalse(hotelId)).thenReturn(Collections.emptyList());
        HotelDTO hotelDTO = HOTEL_MAPPER.toDto(hotelEntity);
        hotelDTO.setDeleted(true);

        toTest.deleteHotel(hotelId);
        assertTrue(HOTEL_MAPPER.toEntity(hotelDTO).getDeleted());

    }

    @Test
    public void test_deleteHotel_withActiveBookings_ok() {

        Long hotelId = 1L;
        HotelEntity hotelEntity = new HotelEntity();
        hotelEntity.setId(hotelId);
        hotelEntity.setDeleted(false);

        when(hotelRepository.findByIdAndDeletedFalse(hotelId)).thenReturn(Optional.of(hotelEntity));

        RoomBookedEntity roomBookedEntity = new RoomBookedEntity();

        BookingEntity bookingEntity = new BookingEntity();
        bookingEntity.setCheckInTime(LocalDateTime.now().plusDays(1));
        bookingEntity.setCheckOutTime(LocalDateTime.now().plusDays(2));

        roomBookedEntity.setBooking(bookingEntity);

        when(roomBookedRepository.findRoomBookedEntitiesByHotelIdAndDeletedFalse(hotelId)).thenReturn(Collections.singletonList(roomBookedEntity));

        assertThrows(GeneralException.class, () -> toTest.deleteHotel(hotelId));
    }
}
