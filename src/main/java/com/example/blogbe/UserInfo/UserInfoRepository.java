package com.example.blogbe.UserInfo;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {
    UserInfo findByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByMail(String mail);


}
