package com.example.blogbe.UserInfo;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class UserInfoController {

    private final UserInfoService userInfoService;

    @PostMapping("/register")
    public UserInfo register(@Valid @RequestBody UserInfo user) {
        return userInfoService.save(user);
    }

    @GetMapping("/users")
    public List<UserInfo> getAllUsers() {
        return userInfoService.getAllUsers();
    }

    @GetMapping("welcome")
    public String welcome() {
        return "welcome";
    }
}
