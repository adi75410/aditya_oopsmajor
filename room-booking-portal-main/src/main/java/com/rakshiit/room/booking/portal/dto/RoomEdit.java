package com.rakshiit.room.booking.portal.dto;

import lombok.Data;

@Data
public class RoomEdit {
    private int roomID;

    private Long roomCapacity;

    private String roomName;
}
