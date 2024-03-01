package com.academy.project.hotelsmanagementsystem.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="rooms_booked")
public class RoomBookedEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private  Integer id;
    @ManyToOne
    @JoinColumn(name = "room_id",referencedColumnName = "id")
    private RoomEntity room;
    @ManyToOne
    @JoinColumn(name = "booking_id",referencedColumnName = "id")
    private BookingEntity booking;
    @Column(name = "price")
    private Integer price;
    @Column(name = "deleted")
    private boolean deleted;
}
