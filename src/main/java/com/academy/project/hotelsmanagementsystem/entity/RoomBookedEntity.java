package com.academy.project.hotelsmanagementsystem.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

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
    private  Long id;
    @ManyToOne
    @JoinColumn(name = "room_id",referencedColumnName = "id")
    private RoomEntity room;
    @ManyToOne
    @JoinColumn(name = "booking_id",referencedColumnName = "id")
    private BookingEntity booking;
    @Column(name = "price")
    private BigDecimal price;
    @Column(name = "deleted")
    private Boolean deleted;
}
