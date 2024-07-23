package com.example.blogbe.UserInfo;

import com.example.blogbe.post.Post;
import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;

@Data
@Entity
@Table(name = "_user")
public class UserInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "name cannot be blank")
    @Size(max = 20, message = "name must be less than 20 characters")
    private String username;

    @Email
    private String mail;

    private String password;
    private String roles;

}