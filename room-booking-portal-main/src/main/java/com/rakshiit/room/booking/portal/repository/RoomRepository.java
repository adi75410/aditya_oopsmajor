package com.rakshiit.room.booking.portal.repository;

import com.rakshiit.room.booking.portal.entity.RoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoomRepository extends JpaRepository<RoomEntity, Integer> {

    Optional<RoomEntity> findRoomByRoomName(String roomName);
    Optional<RoomEntity> findByRoomNameAndRoomIDNot(String roomName, Integer excludedRoomID);
    Optional<RoomEntity> findByRoomCapacity(int capacity);

}
