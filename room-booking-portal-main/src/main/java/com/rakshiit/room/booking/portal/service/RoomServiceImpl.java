package com.rakshiit.room.booking.portal.service;

import com.rakshiit.room.booking.portal.dto.EntityMap;
import com.rakshiit.room.booking.portal.dto.RoomCreation;
import com.rakshiit.room.booking.portal.dto.RoomEdit;
import com.rakshiit.room.booking.portal.entity.RoomEntity;
import com.rakshiit.room.booking.portal.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;

    private static final String ROOM_CREATED_SUCCESSFULLY = "Room created successfully";

    private static final String ROOM_DOES_NOT_EXIST = "Room does not exist";

    @Override
    public ResponseEntity<String> add_Room(RoomCreation roomCreation) {
        if (roomCreation.getRoomCapacity() < 0) {
            return ResponseEntity.badRequest()
                    .body("Invalid Capacity");
        }
        Optional<RoomEntity> optionalRoom = roomRepository.findRoomByRoomName(roomCreation.getRoomName());
        if (optionalRoom.isPresent()) {
            return ResponseEntity.badRequest()
                    .body("Room already exists");
        }
        Random rand = new Random();
        int roomNumber = rand.nextInt(100000);
        roomRepository.save(new RoomEntity(null, roomCreation.getRoomName(), roomNumber,
                roomCreation.getRoomCapacity(), null));
        return ResponseEntity.ok().body(ROOM_CREATED_SUCCESSFULLY);
    }

    @Override
    public ResponseEntity<?> fetchRoom(int capacity) {
        Optional<RoomEntity> optionalRoom = roomRepository.findByRoomCapacity(capacity);
        if (optionalRoom.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Invalid Parameters");
        }
        return ResponseEntity.ok().body(EntityMap.INSTANCE.mapRoomToRoomInfoDTO(optionalRoom.get()));
        // room: roomID<int> capacity<int> /
        // bookings: bookingID<int> dateOfBooking<date> timeFrom<str> timeTo<str> purpose<str> userID<int>
    }

    @Override
    public ResponseEntity<?> editcurrentRoom(RoomEdit roomEdit) {
        if(roomEdit.getRoomCapacity() < 0){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid capacity");
        }

        Optional<RoomEntity> optionalRoom = roomRepository.findById(roomEdit.getRoomID());
        if (optionalRoom.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ROOM_DOES_NOT_EXIST);
        }

        Optional<RoomEntity> optionalRoomByName = roomRepository.findByRoomNameAndRoomIDNot(roomEdit.getRoomName(),
                roomEdit.getRoomID());
        if (optionalRoomByName.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Room with given name already exists");
        }

        RoomEntity room = optionalRoom.get();
        room.setRoomName(roomEdit.getRoomName());
        room.setRoomCapacity(Math.toIntExact(roomEdit.getRoomCapacity()));
        roomRepository.save(room);
        return ResponseEntity.ok("Room edited successfully");
    }

    @Override
    public ResponseEntity<String> destroyRoom(int roomID) {
        Optional<RoomEntity> optionalRoom = roomRepository.findById(roomID);
        if (optionalRoom.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ROOM_DOES_NOT_EXIST);
        }
        roomRepository.delete(optionalRoom.get());
        return ResponseEntity.ok("Room deleted successfully");
    }
}
