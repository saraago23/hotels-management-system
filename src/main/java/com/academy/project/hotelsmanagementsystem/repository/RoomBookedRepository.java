package com.academy.project.hotelsmanagementsystem.repository;

import com.academy.project.hotelsmanagementsystem.entity.RoomBookedEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomBookedRepository extends JpaRepository<RoomBookedEntity,Integer> {
}
