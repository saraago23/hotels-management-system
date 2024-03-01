package com.academy.project.hotelsmanagementsystem.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "hotels")
public class HotelEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;
    @Column(name="hotel_name")
    private String hotelName;
    @Column(name="address")
    private String address;
    @Column(name="post_code")
    private Integer postCode;
    @Column(name="city")
    private String city;
    @Column(name="country")
    private String country;
    @Column(name="rooms_nr")
    private Integer roomsNr;
    @Column(name="phone")
    private String phone;
    @Column(name="star_rating")
    private Integer starRating;
    @Column(name="deleted")
    private boolean deleted;
}
