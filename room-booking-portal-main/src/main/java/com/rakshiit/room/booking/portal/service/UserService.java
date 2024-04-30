package com.rakshiit.room.booking.portal.service;

import com.rakshiit.room.booking.portal.dto.Userinfo;
import com.rakshiit.room.booking.portal.dto.UserLogin;
import com.rakshiit.room.booking.portal.dto.Usersignup;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {

    ResponseEntity<String> handleLogin(UserLogin userLoginDTO);

    ResponseEntity<String> handleSignup(Usersignup usersignup);

    ResponseEntity<?> fetchUserDetail(int userID);

    ResponseEntity<List<Userinfo>> getAllUsers();
}
