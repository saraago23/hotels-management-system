package com.academy.project.hotelsmanagementsystem.controller;

import com.academy.project.hotelsmanagementsystem.dto.UserDTO;
import com.academy.project.hotelsmanagementsystem.service.UserService;
import com.academy.project.hotelsmanagementsystem.service.impl.TokenService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final TokenService tokenService;

    private final UserService userService;

    @PostMapping("/token")
    public String generateToken(Authentication auth){
        var token = tokenService.generateToken(auth);
        return "Bearer ".concat(token);
    }

    @PostMapping("/register")
    public ResponseEntity<UserDTO> registerUser(@RequestParam String role,@RequestBody @Valid UserDTO req){;
        return ResponseEntity.ok(userService.createUser(role,req));
    }
}
