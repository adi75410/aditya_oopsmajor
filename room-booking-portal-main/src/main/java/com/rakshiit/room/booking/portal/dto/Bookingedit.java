package com.rakshiit.room.booking.portal.dto;

import lombok.Builder;
import lombok.Data;

import java.sql.Date;

@Data
@Builder
public class Bookingedit {

    private int userID;

    private int bookingID;

    private int roomID;

    private Date dateOfBooking;

    private String timeTo;

    private String purpose;

    private String timeFrom;
}
