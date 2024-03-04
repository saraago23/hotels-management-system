package com.academy.project.hotelsmanagementsystem.controller;

import com.academy.project.hotelsmanagementsystem.dto.PageDTO;
import com.academy.project.hotelsmanagementsystem.dto.RoleDTO;
import com.academy.project.hotelsmanagementsystem.service.RoleService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/roles")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping
    public ResponseEntity<PageDTO<RoleDTO>> getRoles(@RequestParam(required = false, defaultValue = "0") Integer page,
                                                      @RequestParam(required = false,defaultValue = "10") Integer size){
        Pageable pageable= PageRequest.of(page,size);
        return ResponseEntity.ok(roleService.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleDTO> getRoleById(@PathVariable Integer id){
        return ResponseEntity.ok(roleService.findRoleById(id).orElse(null));
    }

    @PostMapping
    public ResponseEntity<RoleDTO> addRole(@RequestBody RoleDTO role){
        return ResponseEntity.ok(roleService.addRole(role));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoleDTO> updateRole(@PathVariable Integer id,@RequestBody RoleDTO role){
        return ResponseEntity.ok(roleService.updateRole(id,role));
    }
}
