package com.academy.project.hotelsmanagementsystem.repository;

import com.academy.project.hotelsmanagementsystem.entity.HotelEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelRepository extends JpaRepository<HotelEntity,Integer> {
}
