package com.rakshiit.room.booking.portal.service;

import com.rakshiit.room.booking.portal.dto.RoomCreation;
import com.rakshiit.room.booking.portal.dto.RoomEdit;
import org.springframework.http.ResponseEntity;

public interface RoomService {

    ResponseEntity<String> add_Room(RoomCreation roomCreation);

    ResponseEntity<?> fetchRoom(int capacity);

    ResponseEntity<?> editcurrentRoom(RoomEdit roomEdit);

    ResponseEntity<String> destroyRoom(int roomID);
}
