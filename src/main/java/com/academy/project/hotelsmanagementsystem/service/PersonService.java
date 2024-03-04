package com.academy.project.hotelsmanagementsystem.service;

import com.academy.project.hotelsmanagementsystem.dto.PageDTO;
import com.academy.project.hotelsmanagementsystem.dto.PersonDTO;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface PersonService {

    PageDTO<PersonDTO> findAll(Pageable pageable);
    PersonDTO addPerson(@Valid PersonDTO req);
    Optional<PersonDTO> findPersonById(Integer id);
    PersonDTO updatePerson(Integer id,@Valid PersonDTO req);
    void deletePerson(Integer id);
}
