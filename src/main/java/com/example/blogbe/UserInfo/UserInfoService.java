package com.example.blogbe.UserInfo;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserInfoService {


    private final UserInfoRepository userInfoRepository;
    private final PasswordEncoder passwordEncoder;

    public UserInfo save(UserInfo user) {
        if (userInfoRepository.existsByMail(user.getMail())) {
            throw new IllegalArgumentException("Email already in use or blank");
        }

        if (userInfoRepository.existsByUsername(user.getUsername())) {
            throw new IllegalArgumentException("Name already in use or blank");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        if (user.getRoles() == null || user.getRoles().isEmpty()) {
            user.setRoles("USER");
        }

        return userInfoRepository.save(user);
    }

    public List<UserInfo> getAllUsers() {
        return userInfoRepository.findAll();
    }
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        StringBuilder sb = new StringBuilder();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            sb.append(error.getDefaultMessage()).append(" ");
        });
        return new ResponseEntity<>(sb.toString().trim(), HttpStatus.BAD_REQUEST);
    }
}
