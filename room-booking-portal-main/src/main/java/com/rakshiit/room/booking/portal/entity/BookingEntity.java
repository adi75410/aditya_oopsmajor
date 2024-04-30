package com.rakshiit.room.booking.portal.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@Builder
@Entity
@Table(name = "bookings")
@NoArgsConstructor
@AllArgsConstructor
public class BookingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "booking_id", nullable = false)
    private Integer bookingID; // Unique identifier for the booking

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user; // User who made the booking


    @Column(name = "timeFrom")
    private String timeFrom; // Ending time of the booking

    @Column(name = "timeTo")
    private String timeTo;

    @Column(name = "dateOfBooking")
    private Date dateOfBooking; // Ending time of the booking

    @ManyToOne
    @JoinColumn(name = "room_id")
    private RoomEntity room;

    @Column(name = "purpose")
    private String purpose; // Purpose of the booking





}
