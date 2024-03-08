package com.academy.project.hotelsmanagementsystem.repository;

import com.academy.project.hotelsmanagementsystem.entity.RoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<RoomEntity,Long> {
}
