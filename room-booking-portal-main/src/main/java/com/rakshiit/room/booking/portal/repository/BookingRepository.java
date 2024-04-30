package com.rakshiit.room.booking.portal.repository;

import com.rakshiit.room.booking.portal.entity.BookingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookingRepository extends JpaRepository<BookingEntity, Integer> {

    @Query("SELECT b FROM BookingEntity b WHERE b.dateOfBooking <= CURRENT_DATE  AND b.user.userID = :userID")
    List<BookingEntity> findHistory(@Param("userID") int userID);

    @Query("SELECT b FROM BookingEntity b WHERE b.dateOfBooking > CURRENT_DATE  AND b.user.userID = :userID")
    List<BookingEntity> findUpcomingBookings(@Param("userID") int userID);
}
