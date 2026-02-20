package com.example.rentmatch.Service;

import com.example.rentmatch.DTO.LoginResponse;
import com.example.rentmatch.DTO.UserLoginRequest;
import com.example.rentmatch.DTO.UserRegistrationRequest;
import com.example.rentmatch.Entity.User;
import com.example.rentmatch.Repository.UserRepository;
import com.example.rentmatch.Security.JwtFilter;
import com.example.rentmatch.Security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    public User UserRegistration(UserRegistrationRequest request)
    {

        User user = new User();

        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());

        return userRepository.save(user);
    }

    public LoginResponse UserLogin(UserLoginRequest request) {

        Optional<User> optionalUser = userRepository.findByEmail(request.getEmail());

        if(optionalUser.isEmpty()){
            throw new RuntimeException("User not found");
        }

        User user = optionalUser.get();

        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())){
            throw new RuntimeException("Invalid password");
        }

        String token = jwtUtil.generateToken(
                user.getEmail(),
                user.getRole()
        );

        return new LoginResponse(
                token,
                "Bearer",
                user.getEmail(),
                user.getRole()
        );
    }

    public User getUserByEmail(String email) {

        Optional<User> optionalUser = userRepository.findByEmail(email);

        return optionalUser.orElse(null);
    }
}
