package com.academy.project.hotelsmanagementsystem.controller;

import com.academy.project.hotelsmanagementsystem.dto.EmployeeDTO;
import com.academy.project.hotelsmanagementsystem.dto.PageDTO;
import com.academy.project.hotelsmanagementsystem.service.EmployeeService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public ResponseEntity<PageDTO<EmployeeDTO>> getEmployees(@RequestParam(required = false, defaultValue = "0") Integer page,
                                                             @RequestParam(required = false,defaultValue = "10") Integer size){
        Pageable pageable= PageRequest.of(page,size);
        return ResponseEntity.ok(employeeService.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable Integer id){
        return ResponseEntity.ok(employeeService.findEmployeeById(id).orElse(null));
    }

    @PostMapping
    public ResponseEntity<EmployeeDTO> addEmployee(@RequestBody EmployeeDTO employee){
        return ResponseEntity.ok(employeeService.addEmployee(employee));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeDTO> updateEmployee(@PathVariable Integer id,@RequestBody EmployeeDTO employee){
        return ResponseEntity.ok(employeeService.updateEmployee(id,employee));
    }
}
