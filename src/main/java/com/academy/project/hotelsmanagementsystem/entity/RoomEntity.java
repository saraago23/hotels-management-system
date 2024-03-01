package com.academy.project.hotelsmanagementsystem.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="rooms")
public class RoomEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private  Integer id;
    @ManyToOne
    @JoinColumn(name = "room_type_id",referencedColumnName = "id")
    private RoomTypeEntity roomType;
    @ManyToOne
    @JoinColumn(name = "hotel_id",referencedColumnName = "id")
    private HotelEntity hotel;
    @Column(name = "is_booked")
    private boolean isBooked;
    @Column(name = "deleted")
    private boolean deleted;

}
