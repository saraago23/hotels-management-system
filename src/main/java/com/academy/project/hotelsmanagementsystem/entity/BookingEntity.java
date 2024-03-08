package com.academy.project.hotelsmanagementsystem.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="bookings")
public class BookingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private  Long id;
    @ManyToOne
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private UserEntity user;
    @Column(name="total_price")
    private Integer totalPrice;
    @Column(name = "booking_time")
    private LocalDateTime bookingTime;
    @Column(name="check_in_time")
    private LocalDateTime checkInTime;
    @Column(name="check_out_time")
    private LocalDateTime checkOutTime;
    @Column(name="num_adults")
    private Integer numAdults;
    @Column(name="num_children")
    private Integer numChildren;
    @Column(name="special_req")
    private String specialReq;
    @Column(name="deleted")
    private boolean deleted;
}
