package com.rakshiit.room.booking.portal.functions;

import com.rakshiit.room.booking.portal.dto.bookingcreation;
import com.rakshiit.room.booking.portal.dto.Bookingedit;
import com.rakshiit.room.booking.portal.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class Bookingfunctions {

    private final BookingService Booking_Services;

    // Endpoint to create a new booking
    @PostMapping(path = "/book")
    public ResponseEntity<String> DoBooking(@RequestBody bookingcreation bookingcreation) {
        return Booking_Services.DoBooking(bookingcreation);
    }

    // Endpoint to edit a booking
    @GetMapping(path = "/book")
    public ResponseEntity<String> getBooking(@RequestBody Bookingedit bookingedit) {
        return Booking_Services.editBooking(bookingedit);
    }

    // Endpoint to remove a booking
    @DeleteMapping(path = "/book")
    public ResponseEntity<String> Removebooking(@RequestParam int bookingID) {
        return Booking_Services.Removebooking(bookingID);
    }

    // Endpoint to partially update a booking
    @PatchMapping(path = "/book")
    public ResponseEntity<String> Patch_Booking(@RequestBody Bookingedit bookingedit) {
        return Booking_Services.editBooking(bookingedit);
    }

    // Endpoint to retrieve upcoming bookings for a user
    @GetMapping(path = "/upcoming")
    public ResponseEntity<?> getFuturebookings(@RequestParam int userID) {
        return Booking_Services.getFuturebookings(userID);

    }

    // Endpoint to retrieve booking history for a user
    @GetMapping(path = "/history")
    public ResponseEntity<?> RetrieveBookings(@RequestParam int userID) {
        return Booking_Services.RetrieveBookings(userID);

    }
}