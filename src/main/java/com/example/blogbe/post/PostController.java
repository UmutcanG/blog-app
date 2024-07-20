package com.example.blogbe.post;

import com.example.blogbe.UserInfo.UserInfo;
import com.example.blogbe.UserInfo.UserInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    @PostMapping("/add-post")
    @PreAuthorize("hasRole('ADMIN')")
    public Post createPost(@RequestBody Post post, @RequestParam Long authorId) {
        return postService.createPost(post,authorId);
    }

    @GetMapping
    public List<Post> getAllPosts() {
        return postService.findAll();
    }

    @GetMapping("welcome")
    public String welcome() {
        return "welcome";
    }
}
