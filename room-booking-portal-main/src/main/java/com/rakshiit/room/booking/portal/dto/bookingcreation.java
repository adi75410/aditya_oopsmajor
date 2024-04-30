package com.rakshiit.room.booking.portal.dto;

import lombok.Builder;
import lombok.Data;

import java.sql.Date;

@Data
@Builder
public class bookingcreation {

    private int userID;

    private String purpose;

    private Date dateOfBooking;

    private String timeFrom;

    private int roomID;

    private String timeTo;

}
