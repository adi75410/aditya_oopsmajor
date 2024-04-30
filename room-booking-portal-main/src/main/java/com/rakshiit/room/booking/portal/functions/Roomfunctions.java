package com.rakshiit.room.booking.portal.functions;

import com.rakshiit.room.booking.portal.dto.RoomCreation;
import com.rakshiit.room.booking.portal.dto.RoomEdit;
import com.rakshiit.room.booking.portal.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
public class Roomfunctions {

    private final RoomService Room_Services;

    // Endpoint to delete a room
    @DeleteMapping(path = "/rooms")
    public ResponseEntity<String> destroyRoom(@RequestParam int roomID) {
        return Room_Services.destroyRoom(roomID);
    }

    //Endpoint to add a new room
    @PostMapping(path = "/rooms")
    public ResponseEntity<String> add_Room(@RequestBody RoomCreation roomCreation) {
        return Room_Services.add_Room(roomCreation);
    }

    // Endpoint to fetch rooms based on capacity
    @GetMapping(path = "/rooms")
    public ResponseEntity<?> fetchRoom(@RequestParam int capacity) {
        return Room_Services.fetchRoom(capacity);
    }

    // Endpoint to edit room details
    @PatchMapping(path = "/rooms")
    public ResponseEntity<?> editcurrentRoom(@RequestBody RoomEdit roomEdit) {
        return Room_Services.editcurrentRoom(roomEdit);
    }


}
