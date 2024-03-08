package com.academy.project.hotelsmanagementsystem.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="room_types")
public class RoomTypeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "type")
    private String type;
    @Column(name = "price")
    private Integer price;
    @Column(name = "room_desc")
    private String roomDesc;
    @Column(name = "deleted")
    private boolean deleted;
}
