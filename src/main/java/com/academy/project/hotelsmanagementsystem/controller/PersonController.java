package com.academy.project.hotelsmanagementsystem.controller;

import com.academy.project.hotelsmanagementsystem.dto.PageDTO;
import com.academy.project.hotelsmanagementsystem.dto.PersonDTO;
import com.academy.project.hotelsmanagementsystem.service.PersonService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/people")
public class PersonController {

    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping
    public ResponseEntity<PageDTO<PersonDTO>> getPeople(@RequestParam(required = false, defaultValue = "0") Integer page,
                                                        @RequestParam(required = false, defaultValue = "10") Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(personService.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonDTO> getEmployeeById(@PathVariable Integer id) {
        return ResponseEntity.ok(personService.findPersonById(id).orElse(null));
    }

    @PostMapping
    public ResponseEntity<PersonDTO> addEmployee(@RequestBody PersonDTO person) {
        return ResponseEntity.ok(personService.addPerson(person));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PersonDTO> updateEmployee(@PathVariable Integer id, @RequestBody PersonDTO person) {
        return ResponseEntity.ok(personService.updatePerson(id, person));
    }
}
