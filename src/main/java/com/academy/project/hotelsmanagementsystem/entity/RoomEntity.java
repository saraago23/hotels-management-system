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
    private  Long id;
    @Column(name="room_nr")
    private Integer roomNr;
    @ManyToOne
    @JoinColumn(name = "room_type_id",referencedColumnName = "id")
    private RoomTypeEntity roomType;
    @ManyToOne
    @JoinColumn(name = "hotel_id",referencedColumnName = "id")
    private HotelEntity hotel;
    @Column(name = "deleted")
    private Boolean deleted;

}
