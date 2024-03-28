package com.academy.project.hotelsmanagementsystem.controller;

import com.academy.project.hotelsmanagementsystem.dto.PageDTO;
import com.academy.project.hotelsmanagementsystem.dto.RoomDTO;
import com.academy.project.hotelsmanagementsystem.service.RoomService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/rooms")
public class RoomController {

    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping
    public ResponseEntity<PageDTO<RoomDTO>> getRooms(@RequestParam(required = false, defaultValue = "0") Integer page,
                                                     @RequestParam(required = false,defaultValue = "10") Integer size){
        Pageable pageable= PageRequest.of(page,size);
        return ResponseEntity.ok(roomService.findAll(pageable));
    }
    @GetMapping("/available")
    public List<RoomDTO> getAvailableRooms(@RequestParam LocalDateTime checkIn, @RequestParam LocalDateTime checkOut){
        return roomService.getAvailableRooms(checkIn,checkOut);
    }


    @GetMapping("/{id}")
    public ResponseEntity<RoomDTO> getRoomById(@PathVariable Long id){
        return ResponseEntity.ok(roomService.findRoomById(id));
    }

    @PostMapping
    public ResponseEntity<RoomDTO> addRoom(@RequestBody RoomDTO room){
        return ResponseEntity.ok(roomService.addRoom(room));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoomDTO> updateRoom(@PathVariable Long id,@RequestBody RoomDTO room){
        return ResponseEntity.ok(roomService.updateRoom(id,room));
    }
}
