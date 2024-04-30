package com.rakshiit.room.booking.portal.dto;

import lombok.Data;

import java.sql.Date;

@Data
public class Bookinginfo {
    private String room;
    private int roomID;
    private int bookingID;
    private Date dateOfBooking;
    private String timeFrom;
    private String timeTo;
    private String purpose;
    private int userID;
}
