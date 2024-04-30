package com.rakshiit.room.booking.portal.service;


import com.rakshiit.room.booking.portal.dto.bookingcreation;
import com.rakshiit.room.booking.portal.dto.Bookingedit;
import org.springframework.http.ResponseEntity;

public interface BookingService {

    ResponseEntity<String> DoBooking(bookingcreation bookingCreationDTO);

    ResponseEntity<String> editBooking(Bookingedit bookingCreationDTO);

    ResponseEntity<String> Removebooking(int bookingID);

    ResponseEntity<?> RetrieveBookings(int userId);

    ResponseEntity<?> getFuturebookings(int userId);
}
