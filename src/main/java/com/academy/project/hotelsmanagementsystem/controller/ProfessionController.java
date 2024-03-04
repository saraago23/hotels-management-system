package com.academy.project.hotelsmanagementsystem.controller;

import com.academy.project.hotelsmanagementsystem.dto.PageDTO;
import com.academy.project.hotelsmanagementsystem.dto.ProfessionDTO;
import com.academy.project.hotelsmanagementsystem.service.ProfessionService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/professions")
public class ProfessionController {

    private final ProfessionService professionService;

    public ProfessionController(ProfessionService professionService) {
        this.professionService = professionService;
    }

    @GetMapping
    public ResponseEntity<PageDTO<ProfessionDTO>> getProfessions(@RequestParam(required = false, defaultValue = "0") Integer page,
                                                               @RequestParam(required = false,defaultValue = "10") Integer size){
        Pageable pageable= PageRequest.of(page,size);
        return ResponseEntity.ok(professionService.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfessionDTO> getProfessionById(@PathVariable Integer id){
        return ResponseEntity.ok(professionService.findProfessionById(id).orElse(null));
    }

    @PostMapping
    public ResponseEntity<ProfessionDTO> addEmployee(@RequestBody ProfessionDTO profession){
        return ResponseEntity.ok(professionService.addProfession(profession));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProfessionDTO> updateProfession(@PathVariable Integer id,@RequestBody ProfessionDTO profession){
        return ResponseEntity.ok(professionService.updateProfession(id,profession));
    }
}
