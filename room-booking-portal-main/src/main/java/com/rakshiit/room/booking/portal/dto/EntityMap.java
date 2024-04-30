package com.rakshiit.room.booking.portal.dto;

import com.rakshiit.room.booking.portal.entity.BookingEntity;
import com.rakshiit.room.booking.portal.entity.RoomEntity;
import com.rakshiit.room.booking.portal.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface EntityMap {

    EntityMap INSTANCE = Mappers.getMapper(EntityMap.class);

    Userinfo userToUserInfoDTO(UserEntity userEntity);

    @Mapping(target = "capacity", source = "roomCapacity")
    @Mapping(target = "booked", source = "bookings")
    Room_Info mapRoomToRoomInfoDTO(RoomEntity roomEntity);

    @Mapping(target = "room", expression = "java(bookingEntity.getRoom().getRoomName())")
    @Mapping(target = "roomID", expression = "java(bookingEntity.getRoom().getRoomID())")
    @Mapping(target = "userID", expression = "java(bookingEntity.getUser().getUserID())")
    Bookinginfo mapBookingToBookingInfoDTO(BookingEntity bookingEntity);

    List<Bookinginfo> mapBookingsToBookingInfoDTOs(List<BookingEntity> bookings);
}
