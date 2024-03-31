package com.academy.project.hotelsmanagementsystem.controller;

import com.academy.project.hotelsmanagementsystem.dto.PageDTO;
import com.academy.project.hotelsmanagementsystem.dto.RoomBookedDTO;
import com.academy.project.hotelsmanagementsystem.service.RoomBookedService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/roomsbooked")
public class RoomBookedController {

    private final RoomBookedService roomBookedService;

    public RoomBookedController(RoomBookedService roomBookedService) {
        this.roomBookedService = roomBookedService;
    }

    @GetMapping
    public ResponseEntity<PageDTO<RoomBookedDTO>> getRoomsBooked(@RequestParam(required = false, defaultValue = "0") Integer page,
                                                           @RequestParam(required = false,defaultValue = "10") Integer size){
        Pageable pageable= PageRequest.of(page,size);
        return ResponseEntity.ok(roomBookedService.findAll(pageable));
    }
    @GetMapping("/deleted")
    public ResponseEntity<PageDTO<RoomBookedDTO>> getDeletedRoomsBooked(@RequestParam(required = false, defaultValue = "0") Integer page,
                                                                 @RequestParam(required = false,defaultValue = "10") Integer size){
        Pageable pageable= PageRequest.of(page,size);
        return ResponseEntity.ok(roomBookedService.findAllDeleted(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoomBookedDTO> getRoomBookedById(@PathVariable Long id){
        return ResponseEntity.ok(roomBookedService.findRoomBookedById(id));
    }

    @PostMapping
    public ResponseEntity<RoomBookedDTO> addRoomBooked(@RequestBody RoomBookedDTO roomBooked){
        return ResponseEntity.ok(roomBookedService.addRoomBooked(roomBooked));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoomBookedDTO> updateRoomBooked(@PathVariable Long id,@RequestBody RoomBookedDTO roomBooked){
        return ResponseEntity.ok(roomBookedService.updateRoomBooked(id,roomBooked));
    }
}
