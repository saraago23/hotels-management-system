package com.academy.project.hotelsmanagementsystem.service.impl;

import com.academy.project.hotelsmanagementsystem.dto.PageDTO;
import com.academy.project.hotelsmanagementsystem.dto.PersonDTO;
import com.academy.project.hotelsmanagementsystem.entity.PersonEntity;
import com.academy.project.hotelsmanagementsystem.repository.PersonRepository;
import com.academy.project.hotelsmanagementsystem.service.PersonService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import static com.academy.project.hotelsmanagementsystem.utils.PageUtils.*;
import static com.academy.project.hotelsmanagementsystem.mapper.PersonMapper.*;

import java.util.Optional;
@Service
@Validated
public class PersonServiceImpl implements PersonService {

    @Autowired
    private PersonRepository personRepository;
    @Override
    public PageDTO<PersonDTO> findAll(Pageable pageable) {
        return toPageImpl(personRepository.findAll(pageable),PERSON_MAPPER);
    }

    @Override
    public PersonDTO addPerson(@Valid PersonDTO req) {
        var entity=personRepository.save(PERSON_MAPPER.toEntity(req));
        return PERSON_MAPPER.toDto(entity);
    }

    @Override
    public Optional<PersonEntity> findPersonById(Integer id) {
        return personRepository.findById(id);
    }

    @Override
    public PersonDTO updatePerson(Integer id, @Valid PersonDTO req) {
        req.setId(id);
        return personRepository.findById(id)
                .map(o->PERSON_MAPPER.toEntity(req))
                .map(personRepository::save)
                .map(PERSON_MAPPER::toDto)
                .orElse(null);
    }

    @Override
    public void deletePerson(Integer id) {

    }
}
