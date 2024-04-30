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
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private Integer userID; // Unique identifier for the user

    @Column(name = "name")
    private String name; // Name of the user

    @Column(name = "email", unique = true)
    private String email; // Email of the user (unique)

    @Column(name = "password")
    private String password;  //password of user email

    @OneToMany(mappedBy = "user")
    private List<BookingEntity> bookings;  // List of bookings made by the user
}
