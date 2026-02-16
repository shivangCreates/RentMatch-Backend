package com.example.rentmatch.Controller;

import com.example.rentmatch.DTO.LoginResponse;
import com.example.rentmatch.DTO.UserLoginRequest;
import com.example.rentmatch.DTO.UserRegistrationRequest;
import com.example.rentmatch.Entity.User;
import com.example.rentmatch.Security.JwtUtil;
import com.example.rentmatch.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserRegistrationRequest request) {

        User savedUser = userService.UserRegistration(request);

        return ResponseEntity.ok("User Registered Successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody UserLoginRequest request){

        LoginResponse response = userService.UserLogin(request);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/profile")
    public ResponseEntity<User> getProfile(){

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        User user = userService.getUserByEmail(email);

        return ResponseEntity.ok(user);
    }
}
