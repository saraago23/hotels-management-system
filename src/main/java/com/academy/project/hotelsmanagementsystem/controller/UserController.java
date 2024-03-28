package com.academy.project.hotelsmanagementsystem.controller;

import com.academy.project.hotelsmanagementsystem.dto.PageDTO;
import com.academy.project.hotelsmanagementsystem.dto.UserDTO;
import com.academy.project.hotelsmanagementsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<PageDTO<UserDTO>> getUsers(@RequestParam(required = false, defaultValue = "0") Integer page,
                                                     @RequestParam(required = false,defaultValue = "10") Integer size){
        Pageable pageable= PageRequest.of(page,size);
        return ResponseEntity.ok(userService.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id){
        return ResponseEntity.ok(userService.findUserById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id,@RequestBody UserDTO user){
        return ResponseEntity.ok(userService.updateUser(id,user));
    }

    @DeleteMapping("/{id}")

    public ResponseEntity<Void> deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
