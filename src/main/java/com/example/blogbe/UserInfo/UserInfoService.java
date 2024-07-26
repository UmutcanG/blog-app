package com.example.blogbe.UserInfo;


import com.example.blogbe.comment.CommentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    public UserInfo getUserByUsername(String username) {
        return userInfoRepository.findByUsername(username);
    }

    public UserInfo getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            return getUserByUsername(username);
        } else {
            throw new RuntimeException("User is not authenticated");
        }
    }

    public void deleteUser(Long userId) {
        userInfoRepository.deleteById(userId);
    }


}
