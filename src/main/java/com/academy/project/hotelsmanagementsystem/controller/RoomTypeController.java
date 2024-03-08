package com.academy.project.hotelsmanagementsystem.controller;

import com.academy.project.hotelsmanagementsystem.dto.PageDTO;
import com.academy.project.hotelsmanagementsystem.dto.RoomTypeDTO;
import com.academy.project.hotelsmanagementsystem.service.RoomTypeService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/roomtypes")
public class RoomTypeController {

    private final RoomTypeService roomTypeService;

    public RoomTypeController(RoomTypeService roomTypeService) {
        this.roomTypeService = roomTypeService;
    }

    @GetMapping
    public ResponseEntity<PageDTO<RoomTypeDTO>> getRoomTypes(@RequestParam(required = false, defaultValue = "0") Integer page,
                                                                 @RequestParam(required = false,defaultValue = "10") Integer size){
        Pageable pageable= PageRequest.of(page,size);
        return ResponseEntity.ok(roomTypeService.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoomTypeDTO> getRoomTypeById(@PathVariable Long id){
        return ResponseEntity.ok(roomTypeService.findRoomTypeById(id).orElse(null));
    }

    @PostMapping
    public ResponseEntity<RoomTypeDTO> addRoomType(@RequestBody RoomTypeDTO roomType){
        return ResponseEntity.ok(roomTypeService.addRoomType(roomType));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoomTypeDTO> updateRoomType(@PathVariable Long id,@RequestBody RoomTypeDTO roomType){
        return ResponseEntity.ok(roomTypeService.updateRoomType(id,roomType));
    }
}
