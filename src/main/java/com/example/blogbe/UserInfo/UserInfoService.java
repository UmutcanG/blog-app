package com.example.blogbe.UserInfo;


import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserInfoService {


    private final UserInfoRepository userInfoRepository;
    private final PasswordEncoder passwordEncoder;

    public UserInfo save(UserInfo user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userInfoRepository.save(user);
    }

    public List<UserInfo> getAllUsers() {
        return userInfoRepository.findAll();
    }
}
