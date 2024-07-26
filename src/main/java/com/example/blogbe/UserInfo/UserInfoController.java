package com.example.blogbe.UserInfo;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
        return "Welcome! To view the posts on the site, you can click on the 'Posts' tab above.";
    }

    @GetMapping("/current-user")
    public ResponseEntity<UserInfo> getCurrentUser() {
        try {
            UserInfo currentUser = userInfoService.getCurrentUser();
            return ResponseEntity.ok(currentUser);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
    @DeleteMapping("/users/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteUser(@PathVariable Long id) {
        userInfoService.deleteUser(id);
    }

}
