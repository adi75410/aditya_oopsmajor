package com.rakshiit.room.booking.portal.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
@Entity
@Table(name = "rooms")
@NoArgsConstructor
@AllArgsConstructor
public class RoomEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_id", nullable = false)
    private Integer roomID; // Unique identifier for the room

    @Column(name = "roomName", unique = true, nullable = false)
    private String roomName;  //name of room

    @Column(name = "room_number", nullable = false, unique = true)
    private int roomNumber;

    @Column(name = "room_capacity", nullable = false)
    private int roomCapacity;  //total capacity of the room

    @OneToMany(mappedBy = "room")
    private List<BookingEntity> bookings;  // List of bookings associated with the room


}
