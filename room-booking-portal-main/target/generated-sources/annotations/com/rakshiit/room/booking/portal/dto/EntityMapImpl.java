package com.rakshiit.room.booking.portal.dto;

import com.rakshiit.room.booking.portal.entity.BookingEntity;
import com.rakshiit.room.booking.portal.entity.RoomEntity;
import com.rakshiit.room.booking.portal.entity.UserEntity;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-04-30T22:38:47+0530",
    comments = "version: 1.6.0.Beta1, compiler: javac, environment: Java 22 (Oracle Corporation)"
)
public class EntityMapImpl implements EntityMap {

    @Override
    public Userinfo userToUserInfoDTO(UserEntity userEntity) {
        if ( userEntity == null ) {
            return null;
        }

        Userinfo userinfo = new Userinfo();

        userinfo.setName( userEntity.getName() );
        userinfo.setUserID( userEntity.getUserID() );
        userinfo.setEmail( userEntity.getEmail() );

        return userinfo;
    }

    @Override
    public Room_Info mapRoomToRoomInfoDTO(RoomEntity roomEntity) {
        if ( roomEntity == null ) {
            return null;
        }

        Room_Info.Room_InfoBuilder room_Info = Room_Info.builder();

        room_Info.capacity( roomEntity.getRoomCapacity() );
        room_Info.booked( mapBookingsToBookingInfoDTOs( roomEntity.getBookings() ) );
        if ( roomEntity.getRoomID() != null ) {
            room_Info.roomID( roomEntity.getRoomID() );
        }

        return room_Info.build();
    }

    @Override
    public Bookinginfo mapBookingToBookingInfoDTO(BookingEntity bookingEntity) {
        if ( bookingEntity == null ) {
            return null;
        }

        Bookinginfo bookinginfo = new Bookinginfo();

        if ( bookingEntity.getBookingID() != null ) {
            bookinginfo.setBookingID( bookingEntity.getBookingID() );
        }
        bookinginfo.setDateOfBooking( bookingEntity.getDateOfBooking() );
        bookinginfo.setTimeFrom( bookingEntity.getTimeFrom() );
        bookinginfo.setTimeTo( bookingEntity.getTimeTo() );
        bookinginfo.setPurpose( bookingEntity.getPurpose() );

        bookinginfo.setRoom( bookingEntity.getRoom().getRoomName() );
        bookinginfo.setRoomID( bookingEntity.getRoom().getRoomID() );
        bookinginfo.setUserID( bookingEntity.getUser().getUserID() );

        return bookinginfo;
    }

    @Override
    public List<Bookinginfo> mapBookingsToBookingInfoDTOs(List<BookingEntity> bookings) {
        if ( bookings == null ) {
            return null;
        }

        List<Bookinginfo> list = new ArrayList<Bookinginfo>( bookings.size() );
        for ( BookingEntity bookingEntity : bookings ) {
            list.add( mapBookingToBookingInfoDTO( bookingEntity ) );
        }

        return list;
    }
}
