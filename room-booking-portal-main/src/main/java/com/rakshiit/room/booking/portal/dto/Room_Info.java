package com.rakshiit.room.booking.portal.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Room_Info {

    private int roomID;

    private int capacity;

    private List<Bookinginfo> booked;

}
