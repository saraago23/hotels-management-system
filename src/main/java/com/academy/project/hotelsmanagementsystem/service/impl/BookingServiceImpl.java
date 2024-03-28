package com.academy.project.hotelsmanagementsystem.service.impl;

import com.academy.project.hotelsmanagementsystem.dto.*;
import com.academy.project.hotelsmanagementsystem.entity.BookingEntity;
import com.academy.project.hotelsmanagementsystem.entity.RoomBookedEntity;
import com.academy.project.hotelsmanagementsystem.entity.RoomEntity;
import com.academy.project.hotelsmanagementsystem.entity.UserEntity;
import com.academy.project.hotelsmanagementsystem.exceptions.GeneralException;
import com.academy.project.hotelsmanagementsystem.repository.*;
import com.academy.project.hotelsmanagementsystem.service.BookingService;
import com.academy.project.hotelsmanagementsystem.utils.UserUtils;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.academy.project.hotelsmanagementsystem.utils.PageUtils.*;
import static com.academy.project.hotelsmanagementsystem.mapper.BookingMapper.*;
import static com.academy.project.hotelsmanagementsystem.mapper.UserMapper.*;

@Service
@Validated
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final RoomBookedRepository roomBookedRepository;
    private final RoomRepository roomRepository;

    @Override
    public PageDTO<BookingDTO> findAll(Pageable pageable) {
        return toPageImpl(bookingRepository.findAllNonDeleted(pageable), BOOKING_MAPPER);
    }

    @Override
    public PageDTO<BookingDTO> findAllDeleted(Pageable pageable) {
        return toPageImpl(bookingRepository.findAllDeleted(pageable), BOOKING_MAPPER);
    }

    @Override
    @Transactional
    public BookingDTO addBooking(@Valid CreateBookingDTO bookingDto) {

        UserEntity createdUser = userRepository.findByUsername(UserUtils.getLoggedUser()).orElseThrow(() -> new GeneralException("User: " + UserUtils.getLoggedUser() + " was not found"));

        if (bookingDto.getCheckOutTime().isBefore(bookingDto.getCheckInTime())) {
            throw new GeneralException("Check out time can not be before check in time!");
        }

        List<RoomEntity> roomEntities = bookingDto.getRoomDTOList().stream().map(roomDTO -> roomRepository.findById(roomDTO.getId()).orElseThrow(() -> new GeneralException("Room with id: " + roomDTO.getId() + " is not found!"))).collect(Collectors.toList());

        if (!areRoomsAvailable(null, roomEntities, bookingDto.getCheckInTime(), bookingDto.getCheckOutTime())) {
            throw new GeneralException("Check the availability of rooms!");
        }

        Integer maxAllowedGuests = roomEntities.stream()
                .mapToInt(room -> room.getRoomType().getNumGuest())
                .sum();

        if (bookingDto.getTotalNumGuests() > maxAllowedGuests) {
            throw new GeneralException("You have exceeded the number of guests!");
        }

        BookingEntity newBooking = bookingRepository.save(BOOKING_MAPPER.toEntity(BookingDTO.builder()
                .user(USER_MAPPER.toDto(createdUser))
                .bookingTime(LocalDateTime.now())
                .checkInTime(bookingDto.getCheckInTime())
                .checkOutTime(bookingDto.getCheckOutTime())
                .deleted(false)
                .totalPrice(BigDecimal.ZERO)
                .totalNumGuests(bookingDto.getTotalNumGuests())
                .build()));

        BigDecimal totalPrice = BigDecimal.ZERO;

        for (RoomEntity room : roomEntities) {
            RoomBookedEntity roomBookedEntity = RoomBookedEntity.builder()
                    .room(room)
                    .booking(newBooking)
                    .price(room.getRoomType().getPrice())
                    .deleted(false)
                    .build();
            roomBookedRepository.save(roomBookedEntity);

            totalPrice = totalPrice.add(room.getRoomType().getPrice());
        }

        newBooking.setTotalPrice(totalPrice);

        return BOOKING_MAPPER.toDto(bookingRepository.save(newBooking));
    }

    private boolean areRoomsAvailable(BookingEntity bookingEntity, List<RoomEntity> roomEntityList, LocalDateTime checkInTime, LocalDateTime checkOutTime) {

        for (RoomEntity room : roomEntityList) {
            List<RoomBookedEntity> roomBookings = roomBookedRepository.findRoomBookedByRoom(room);
            for (RoomBookedEntity bookingOfRoom : roomBookings) {
                if (!((checkInTime.isBefore(bookingOfRoom.getBooking().getCheckInTime()) && checkOutTime.isBefore(bookingOfRoom.getBooking().getCheckInTime()))
                        || (checkInTime.isAfter(bookingOfRoom.getBooking().getCheckOutTime()) && checkOutTime.isAfter(bookingOfRoom.getBooking().getCheckOutTime())))) {
                    if (!(bookingEntity != null && bookingEntity.equals(bookingOfRoom.getBooking()))) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    @Override
    public BookingDTO findBookingById(Long id) {
        BookingEntity bookingEntity = bookingRepository.findById(id).orElseThrow(() -> new GeneralException("Booking with id: " + id + " was not found"));

        if (bookingEntity.getDeleted()) {
            throw new GeneralException("This booking no longer exists");
        }

        if (isUserAllowed(bookingEntity.getUser())) {
            throw new GeneralException("You are not allowed to access this feature!");
        }
        return BOOKING_MAPPER.toDto(bookingEntity);
    }

    @Override
    public List<BookingDTO> findBookingsByUserId(Long id) {

        if (!userRepository.existsById(id)){
            throw new GeneralException("This user does not exist");
        }
            List<BookingEntity> bookings = bookingRepository.findBookingsByUserId(id);
        List<BookingDTO> bookingDTOS= new ArrayList<>();
        for(BookingEntity bookingEntity:bookings){
            bookingDTOS.add(BOOKING_MAPPER.toDto(bookingEntity));
        }

        return bookingDTOS;
    }

    @Override
    public BookingDTO updateBooking(Long id, @Valid UpdateBookingDTO createUpdateBookingDTO) {
        BookingEntity bookingToBeUpdated = bookingRepository.findById(id).orElseThrow(() -> new GeneralException("Booking with id: " + id + " does not exist"));

        if (isUserAllowed(bookingToBeUpdated.getUser())) {
            throw new GeneralException("You are not allowed to access this feature!");
        }

        if (createUpdateBookingDTO.getCheckOutTime().isBefore(createUpdateBookingDTO.getCheckInTime())) {
            throw new GeneralException("Check out time can not be before check in time!");
        }

        List<RoomBookedEntity> bookedRooms = roomBookedRepository.findRoomBookedByBookingId(bookingToBeUpdated.getId());

        List<RoomEntity> roomEntities = bookedRooms.stream().map(RoomBookedEntity::getRoom).toList();

        if (!areRoomsAvailable(bookingToBeUpdated, roomEntities, createUpdateBookingDTO.getCheckInTime(), createUpdateBookingDTO.getCheckOutTime())) {
            throw new GeneralException("Rooms are not available for requested dates");
        }

        Integer maxAllowedGuests = bookedRooms.stream()
                .mapToInt(roomBookedEntity -> roomBookedEntity.getRoom().getRoomType().getNumGuest())
                .sum();

        if (createUpdateBookingDTO.getTotalNumGuests() > maxAllowedGuests) {
            throw new GeneralException("You have exceeded the number of guests!");
        }

        BookingDTO updatedDto = BOOKING_MAPPER.toDto(bookingToBeUpdated);
        updatedDto.setCheckOutTime(createUpdateBookingDTO.getCheckOutTime());
        updatedDto.setCheckInTime(createUpdateBookingDTO.getCheckInTime());
        updatedDto.setTotalNumGuests(createUpdateBookingDTO.getTotalNumGuests());
        updatedDto.setDeleted(false);
        updatedDto.setSpecialReq(createUpdateBookingDTO.getSpecialReq());

        return BOOKING_MAPPER.toDto(bookingRepository.save(BOOKING_MAPPER.toEntity(updatedDto)));
    }

   /* private Boolean areRoomsAvailable(BookingEntity booking, List<RoomBookedEntity> bookedRooms, LocalDateTime checkInTime, LocalDateTime checkOutTime) {
        boolean flag = true;

        for (RoomBookedEntity roomBooked : bookedRooms) {
            List<RoomBookedEntity> roomBookings = roomBookedRepository.findRoomBookedByRoom(roomBooked.getRoom());
            for (RoomBookedEntity rb : roomBookings) {
                if (rb.getBooking() != booking) {
                    var bookingOfRoom = rb.getBooking();
                    if (!((checkInTime.isBefore(bookingOfRoom.getCheckInTime()) && checkOutTime.isBefore(bookingOfRoom.getCheckInTime()))
                            || (checkInTime.isAfter(bookingOfRoom.getCheckOutTime()) && checkOutTime.isAfter(bookingOfRoom.getCheckOutTime())))) {
                        flag = false;
                    }
                }
            }
        }
        return flag;
    }*/

    @Override
    public void deleteBooking(Long id) {

        BookingEntity bookingToBeDeleted = bookingRepository.findById(id).orElseThrow(() -> new GeneralException("There is no booking with id: " + id + " to be deleted"));

        if(bookingToBeDeleted.getCheckOutTime().isBefore(LocalDateTime.now())){
            throw new GeneralException("You can not delete a booking that has already happened");
        }
        if (bookingToBeDeleted.getCheckInTime().isBefore(LocalDateTime.now())){
            throw new GeneralException("THe guests have already checked in");
        }
        bookingToBeDeleted.setDeleted(true);

        List<RoomBookedEntity> roomsBooked = roomBookedRepository.findRoomBookedByBookingId(id);
        for (RoomBookedEntity roomBookedEntity : roomsBooked) {
            roomBookedEntity.setDeleted(true);
            roomBookedRepository.save(roomBookedEntity);
        }
        bookingRepository.save(bookingToBeDeleted);
    }

    public Boolean isUserAllowed(UserEntity user) {
        UserEntity loggedUser = userRepository.findByUsername(UserUtils.getLoggedUser())
                .orElseThrow(() -> new GeneralException("User : " + UserUtils.getLoggedUser() + " was not found"));

        return !("ADMIN".equalsIgnoreCase(loggedUser.getRole().getTitle()) || loggedUser.equals(user));
    }
}
