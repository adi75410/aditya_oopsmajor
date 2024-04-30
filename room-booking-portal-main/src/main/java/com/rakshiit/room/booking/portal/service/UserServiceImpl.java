package com.rakshiit.room.booking.portal.service;

import com.rakshiit.room.booking.portal.dto.EntityMap;
import com.rakshiit.room.booking.portal.dto.Userinfo;
import com.rakshiit.room.booking.portal.dto.UserLogin;
import com.rakshiit.room.booking.portal.dto.Usersignup;
import com.rakshiit.room.booking.portal.entity.UserEntity;
import com.rakshiit.room.booking.portal.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public ResponseEntity<String> handleLogin(UserLogin userlogin) {
        Optional<UserEntity> userOptional = userRepository.findByEmail(userlogin.getEmail());

        if (userOptional.isPresent()) {
            if (userOptional.get().getPassword().equals(userlogin.getPassword())) {
                return ResponseEntity.ok("Login Successful");
            }
            ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Username/Password Incorrect");
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(("User does not exist"));
    }

    @Override
    public ResponseEntity<String> handleSignup(Usersignup usersignup) {
        Optional<UserEntity> userOptional = userRepository.findByEmail(usersignup.getEmail());
        if (userOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Forbidden, Account already exists");
        }
        UserEntity user = UserEntity.builder()
                .email(usersignup.getEmail())
                .name(usersignup.getName())
                .password(usersignup.getPassword())
                .build();

        userRepository.save(user);
        return ResponseEntity.ok("Account Creation Successful");
    }

    @Override
    public ResponseEntity<?> fetchUserDetail(int userID) {
        Optional<UserEntity> userOptional = userRepository.findById(userID);
        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("User does not exist");
        }
        return ResponseEntity.ok(EntityMap.INSTANCE.userToUserInfoDTO(userOptional.get()));
    }

    @Override
    public ResponseEntity<List<Userinfo>> getAllUsers() {
        List<UserEntity> userList = userRepository.findAll();
        return ResponseEntity.ok(userList.stream()
                .map(EntityMap.INSTANCE::userToUserInfoDTO)
                .toList());
    }
}
