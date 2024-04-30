package com.rakshiit.room.booking.portal.service;

import com.rakshiit.room.booking.portal.dto.bookingcreation;
import com.rakshiit.room.booking.portal.dto.Bookingedit;
import com.rakshiit.room.booking.portal.dto.EntityMap;
import com.rakshiit.room.booking.portal.entity.BookingEntity;
import com.rakshiit.room.booking.portal.entity.RoomEntity;
import com.rakshiit.room.booking.portal.entity.UserEntity;
import com.rakshiit.room.booking.portal.repository.BookingRepository;
import com.rakshiit.room.booking.portal.repository.RoomRepository;
import com.rakshiit.room.booking.portal.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;

    private final UserRepository userRepository;

    private final RoomRepository roomRepository;

    private static final String TIME_PATTERN = "HH:mm:ss";

    private static final String ROOM_DOES_NOT_EXIST = "Room does not exist";

    private static final String USER_DOES_NOT_EXIST = "User does not exist";

    private static final String ROOM_UNAVAILABLE = "Room unavailable";

    private static final String INVALID_DATE_TIME = "Invalid date/time";

    @Override
    public ResponseEntity<String> DoBooking(bookingcreation bookingCreationDTO) {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern(TIME_PATTERN);
        LocalTime timeFrom = LocalTime.parse(bookingCreationDTO.getTimeFrom(), timeFormatter);
        LocalTime timeTo = LocalTime.parse(bookingCreationDTO.getTimeTo(), timeFormatter);

        Optional<RoomEntity> room = roomRepository.findById(bookingCreationDTO.getRoomID());
        if (room.isEmpty()) {
            return ResponseEntity.badRequest().body(ROOM_DOES_NOT_EXIST);
        }

        Optional<UserEntity> optionalUser = userRepository.findById(bookingCreationDTO.getUserID());
        if (optionalUser.isEmpty()) {
            return ResponseEntity.badRequest().body(USER_DOES_NOT_EXIST);
        }

        if (isRoomNotAvailable(room.get(), bookingCreationDTO.getDateOfBooking(), timeFrom, timeTo, null)) {
            return ResponseEntity.badRequest().body(ROOM_UNAVAILABLE);
        }

        if (timeFrom.isAfter(timeTo) || bookingCreationDTO.getDateOfBooking().toLocalDate().isBefore(LocalDate.now())) {
            return ResponseEntity.badRequest().body(INVALID_DATE_TIME);
        }

        bookingRepository.save(BookingEntity.builder()
                .dateOfBooking(bookingCreationDTO.getDateOfBooking())
                .timeFrom(bookingCreationDTO.getTimeFrom())
                .timeTo(bookingCreationDTO.getTimeTo())
                .purpose(bookingCreationDTO.getPurpose())
                .room(room.get())
                .user(optionalUser.get())
                .build());
        return ResponseEntity.ok("Booking created successfully");
    }

    @Override
    public ResponseEntity<String> editBooking(Bookingedit bookingEditDTO) {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern(TIME_PATTERN);
        LocalTime timeFrom = LocalTime.parse(bookingEditDTO.getTimeFrom(), timeFormatter);
        LocalTime timeTo = LocalTime.parse(bookingEditDTO.getTimeTo(), timeFormatter);

        Optional<RoomEntity> room = roomRepository.findById(bookingEditDTO.getRoomID());
        if (room.isEmpty()) {
            return ResponseEntity.badRequest().body(ROOM_DOES_NOT_EXIST);
        }

        Optional<BookingEntity> optionalBooking = bookingRepository.findById(bookingEditDTO.getBookingID());
        if (optionalBooking.isEmpty()) {
            return ResponseEntity.badRequest().body("Booking does not exist");
        }

        Optional<UserEntity> optionalUser = userRepository.findById(bookingEditDTO.getUserID());
        if (optionalUser.isEmpty()) {
            return ResponseEntity.badRequest().body(USER_DOES_NOT_EXIST);
        }

        if (isRoomNotAvailable(room.get(), bookingEditDTO.getDateOfBooking(), timeFrom, timeTo, bookingEditDTO.getBookingID())) {
            return ResponseEntity.badRequest().body(ROOM_UNAVAILABLE);
        }

        if (bookingEditDTO.getDateOfBooking().toLocalDate().isBefore(LocalDate.now())) {
            return ResponseEntity.badRequest().body(INVALID_DATE_TIME);
        }

        BookingEntity booking = optionalBooking.get();
        booking.setDateOfBooking(bookingEditDTO.getDateOfBooking());
        booking.setTimeFrom(bookingEditDTO.getTimeFrom());
        booking.setTimeTo(bookingEditDTO.getTimeTo());
        booking.setPurpose(bookingEditDTO.getPurpose());
        bookingRepository.save(booking);

        return ResponseEntity.ok("Booking modified successfully");
    }

    @Override
    public ResponseEntity<String> Removebooking(int bookingID) {
        Optional<BookingEntity> optionalBooking = bookingRepository.findById(bookingID);
        if (optionalBooking.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Booking does not exist");
        }
        bookingRepository.delete(optionalBooking.get());
        return ResponseEntity.ok("Booking deleted successfully");
    }

    @Override
    public ResponseEntity<?> RetrieveBookings(int userID) {
        Optional<UserEntity> optionalUser = userRepository.findById(userID);
        if (optionalUser.isEmpty()) {
            return ResponseEntity.badRequest().body(USER_DOES_NOT_EXIST);
        }
        List<BookingEntity> oldBookings = bookingRepository.findHistory(userID);
        return ResponseEntity.ok().body(EntityMap.INSTANCE.mapBookingsToBookingInfoDTOs(oldBookings));
    }

    @Override
    public ResponseEntity<?> getFuturebookings(int userID) {
        Optional<UserEntity> optionalUser = userRepository.findById(userID);
        if (optionalUser.isEmpty()) {
            return ResponseEntity.badRequest().body(USER_DOES_NOT_EXIST);
        }
        List<BookingEntity> upcomingBookings = bookingRepository.findUpcomingBookings(userID);
        return ResponseEntity.ok().body(EntityMap.INSTANCE.mapBookingsToBookingInfoDTOs(upcomingBookings));
    }


    public boolean isRoomNotAvailable(RoomEntity room, Date requestedDate,
                                      LocalTime requestedTimeFrom,
                                      LocalTime requestedTimeTo,
                                      Integer excludedBookingId) {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern(TIME_PATTERN);

        return room.getBookings().stream()
                .filter(booking -> !booking.getBookingID().equals(excludedBookingId))
                .anyMatch(booking -> {
                    LocalDate bookingLocalDate = booking.getDateOfBooking().toLocalDate();
                    LocalDate requestedLocalDate = requestedDate.toLocalDate();

                    LocalTime bookingTimeFrom = LocalTime.parse(booking.getTimeFrom(), timeFormatter);
                    LocalTime bookingTimeTo = LocalTime.parse(booking.getTimeTo(), timeFormatter);

                    return requestedLocalDate.isEqual(bookingLocalDate) &&
                            isTimeNotInAvalaibleRange(requestedTimeFrom, requestedTimeTo, bookingTimeFrom, bookingTimeTo);
                });
    }

    private static boolean isTimeNotInAvalaibleRange(LocalTime timeFrom, LocalTime timeTo, LocalTime lowerLimit, LocalTime upperLimit) {
        return (isIntersectingTime(timeFrom, timeTo, lowerLimit, upperLimit)
                || isBetweenTime(timeFrom, timeTo, lowerLimit, upperLimit)
                || isEncapsulatingTime(timeFrom, timeTo, lowerLimit, upperLimit)
                || timeFrom.equals(lowerLimit)
                || timeFrom.equals(upperLimit)
                || timeTo.equals(lowerLimit)
                || timeTo.equals(upperLimit));
    }

    private static boolean isIntersectingTime(LocalTime timeFrom, LocalTime timeTo, LocalTime lowerLimit, LocalTime upperLimit) {
        return (timeTo.isAfter(lowerLimit) && timeTo.isBefore(upperLimit)) || (timeFrom.isBefore(upperLimit) && timeFrom.isAfter(lowerLimit));
    }

    private static boolean isBetweenTime(LocalTime timeFrom, LocalTime timeTo, LocalTime lowerLimit, LocalTime upperLimit) {
        return (timeFrom.isAfter(lowerLimit) && timeTo.isBefore(upperLimit));
    }

    private static boolean isEncapsulatingTime(LocalTime timeFrom, LocalTime timeTo, LocalTime lowerLimit, LocalTime upperLimit) {
        return (timeFrom.isBefore(lowerLimit) && timeTo.isAfter(upperLimit));
    }

}
