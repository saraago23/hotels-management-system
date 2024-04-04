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
import org.springframework.beans.factory.annotation.Autowired;
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
import static com.academy.project.hotelsmanagementsystem.mapper.RoomMapper.*;

@Service
@Validated
public class BookingServiceImpl implements BookingService {

    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoomBookedRepository roomBookedRepository;
    @Autowired
    private RoomRepository roomRepository;

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
    public BookingDTO addBooking(@Valid CreateUpdateBookingDTO bookingDto) {

        UserEntity loggedUser = userRepository.findByUsernameAndDeletedFalse(UserUtils.getLoggedUser()).orElseThrow(() -> new GeneralException("User: " + UserUtils.getLoggedUser() + " was not found"));

        if (bookingDto.getCheckOutTime().isBefore(bookingDto.getCheckInTime())) {
            throw new GeneralException("Check out time can not be before check in time!");
        }

        List<RoomEntity> roomEntities = bookingDto.getRoomDTOList().stream().map(roomDTO -> roomRepository.findByIdAndDeletedFalse(roomDTO.getId()).orElseThrow(() -> new GeneralException("No room with id: " + roomDTO.getId() + " was found"))).collect(Collectors.toList());

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
                .user(USER_MAPPER.toDto(loggedUser))
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
            List<RoomBookedEntity> roomBookings = roomBookedRepository.findRoomBookedByRoomAndDeletedFalse(room);
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
        BookingEntity bookingEntity = bookingRepository.findByIdAndDeletedFalse(id).orElseThrow(() -> new GeneralException("Booking with id: " + id + " was not found"));

        if (isUserAllowed(bookingEntity.getUser())) {
            throw new GeneralException("You are not allowed to access this feature!");
        }


        return BOOKING_MAPPER.toDto(bookingEntity);
    }

    @Override
    public List<BookingDTO> findBookingsByUserId(Long userId) {

        UserEntity userEntity = userRepository.findByIdAndDeletedFalse(userId).orElseThrow(() -> new GeneralException("User with id : " + userId + " was not found"));
        if (isUserAllowed(userEntity)) {
            throw new GeneralException("You have no access over this booking!");
        }
        List<BookingEntity> bookings = bookingRepository.findBookingsByUserAndDeletedFalse(userEntity);


        return bookings.stream().map(BOOKING_MAPPER::toDto).collect(Collectors.toList());
    }

    @Override
    public List<BookingDTO> findMyBookings() {

        UserEntity loggedUser = userRepository.findByUsernameAndDeletedFalse(UserUtils.getLoggedUser()).orElseThrow(() -> new GeneralException("User not found on the db"));
        List<BookingEntity> bookings = bookingRepository.findBookingsByUserAndDeletedFalse(loggedUser);

        return bookings.stream().map(BOOKING_MAPPER::toDto).collect(Collectors.toList());
    }

    @Override

    public DisplayBookingDTO updateBooking(Long id, @Valid CreateUpdateBookingDTO updateBookingDTO) {
        BookingEntity bookingToBeUpdated = bookingRepository.findByIdAndDeletedFalse(id).orElseThrow(() -> new GeneralException("Booking with id: " + id + " does not exist"));

        if (isUserAllowed(bookingToBeUpdated.getUser())) {
            throw new GeneralException("You have no access over this booking!");
        }

        if (updateBookingDTO.getCheckOutTime().isBefore(updateBookingDTO.getCheckInTime())) {
            throw new GeneralException("Check out time can not be before check in time!");
        }

        List<RoomBookedEntity> oldBookedRooms = roomBookedRepository.findRoomBookedByBookingIdAndDeletedFalse(bookingToBeUpdated.getId());

        List<RoomEntity> newRooms = new ArrayList<>(updateBookingDTO.getRoomDTOList().stream().map(room -> roomRepository.findByIdAndDeletedFalse(room.getId()).orElseThrow(() -> new GeneralException("No room with id: " + room.getId() + " was found on the db"))).toList());

        for (RoomBookedEntity roomBooked : oldBookedRooms) {
            if (!newRooms.contains(roomBooked.getRoom())) {
                roomBooked.setDeleted(true);
                roomBookedRepository.save(roomBooked);
            }
            newRooms.remove(roomBooked.getRoom());
        }

        if (!areRoomsAvailable(bookingToBeUpdated, newRooms, updateBookingDTO.getCheckInTime(), updateBookingDTO.getCheckOutTime())) {
            throw new GeneralException("Rooms are not available for requested dates");
        }

        for (RoomEntity room : newRooms) {
            RoomBookedEntity roomBooked = RoomBookedEntity.builder()
                    .room(room)
                    .booking(bookingToBeUpdated)
                    .price(room.getRoomType().getPrice())
                    .deleted(false)
                    .build();
            roomBookedRepository.save(roomBooked);
        }

        List<RoomBookedEntity> updatedRoomsBooked = roomBookedRepository.findRoomBookedByBookingIdAndDeletedFalse(bookingToBeUpdated.getId());

        Integer maxAllowedGuests = updatedRoomsBooked.stream()
                .mapToInt(roomBooked -> roomBooked.getRoom().getRoomType().getNumGuest())
                .sum();

        if (updateBookingDTO.getTotalNumGuests() > maxAllowedGuests) {
            throw new GeneralException("You have exceeded the number of guests!");
        }
        BigDecimal totalPrice = BigDecimal.valueOf(updatedRoomsBooked.stream().mapToDouble(roomBooked -> roomBooked.getRoom().getRoomType().getPrice().doubleValue()).sum());

        BookingDTO updatedDto = BOOKING_MAPPER.toDto(bookingToBeUpdated);
        updatedDto.setCheckOutTime(updateBookingDTO.getCheckOutTime());
        updatedDto.setCheckInTime(updateBookingDTO.getCheckInTime());
        updatedDto.setTotalNumGuests(updateBookingDTO.getTotalNumGuests());
        updatedDto.setTotalPrice(totalPrice);
        updatedDto.setDeleted(false);
        updatedDto.setSpecialReq(updateBookingDTO.getSpecialReq());

        BookingEntity updatedBooking = bookingRepository.save(BOOKING_MAPPER.toEntity(updatedDto));

        return DisplayBookingDTO.builder()
                .firstName(updatedBooking.getUser().getFirstName())
                .lastName(updatedBooking.getUser().getLastName())
                .totalPrice(updatedBooking.getTotalPrice())
                .specialReq(updatedBooking.getSpecialReq())
                .checkInTime(updatedBooking.getCheckInTime())
                .checkOutTime(updatedBooking.getCheckOutTime())
                .totalNumGuests(updatedBooking.getTotalNumGuests())
                .rooms(updatedRoomsBooked.stream().map(roomBooked -> ROOM_MAPPER.toDto(roomBooked.getRoom())).toList())
                .build();
    }

    @Override
    public void deleteBooking(Long id) {

        BookingEntity bookingToBeDeleted = bookingRepository.findByIdAndDeletedFalse(id).orElseThrow(() -> new GeneralException("There is no booking with id: " + id + " to be deleted"));


        if (isUserAllowed(bookingToBeDeleted.getUser())) {
            throw new GeneralException("You have no access over this booking!");
        }

        if (bookingToBeDeleted.getCheckOutTime().isBefore(LocalDateTime.now())) {
            throw new GeneralException("You can not delete a booking that has already happened");
        }
        if (bookingToBeDeleted.getCheckInTime().isBefore(LocalDateTime.now())) {
            throw new GeneralException("THe guests have already checked in");
        }
        bookingToBeDeleted.setDeleted(true);

        List<RoomBookedEntity> roomsBooked = roomBookedRepository.findRoomBookedByBookingIdAndDeletedFalse(id);

        for (RoomBookedEntity roomBookedEntity : roomsBooked) {
            roomBookedEntity.setDeleted(true);
            roomBookedRepository.save(roomBookedEntity);
        }
        bookingRepository.save(bookingToBeDeleted);
    }

    public Boolean isUserAllowed(UserEntity user) {
        UserEntity loggedUser = userRepository.findByUsernameAndDeletedFalse(UserUtils.getLoggedUser())
                .orElseThrow(() -> new GeneralException("User : " + UserUtils.getLoggedUser() + " was not found"));

        return !("ADMIN".equalsIgnoreCase(loggedUser.getRole().getTitle()) || loggedUser.equals(user));
    }
}
