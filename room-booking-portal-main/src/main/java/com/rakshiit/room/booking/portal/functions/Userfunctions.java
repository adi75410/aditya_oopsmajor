package com.rakshiit.room.booking.portal.functions;

import com.rakshiit.room.booking.portal.dto.Userinfo;
import com.rakshiit.room.booking.portal.dto.UserLogin;
import com.rakshiit.room.booking.portal.dto.Usersignup;
import com.rakshiit.room.booking.portal.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class Userfunctions {

    private final UserService User_Services;

    //endpoint for user signup
    @PostMapping(path = "/signup")
    public ResponseEntity<String> Signup(@RequestBody Usersignup usersignup) {
        return User_Services.handleSignup(usersignup);
    }

    //endpoint for user login
    @PostMapping(path = "/login")
    public ResponseEntity<String> Login(@RequestBody UserLogin userLogin) {
        return User_Services.handleLogin(userLogin);
    }

    //endpoint for getting all users
    @GetMapping(path = "/users")
    public ResponseEntity<List<Userinfo>> getAllUsers() {
        return User_Services.getAllUsers();
    }

    //endpoint to partially fetch user
    @PostMapping(path = "/user")
    public ResponseEntity<?> fetchUse(@RequestParam int userID) {
        return User_Services.fetchUserDetail(userID);
    }

    //endpoint to fetch user
    @GetMapping(path = "/user")
    public ResponseEntity<?> fetchUser(@RequestParam int userID) {
        return User_Services.fetchUserDetail(userID);
    }


}
