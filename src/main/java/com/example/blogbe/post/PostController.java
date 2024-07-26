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
    @PreAuthorize("hasAuthority('ADMIN')")
    public Post createPost(@RequestBody Post post) {
        return postService.createPost(post);
    }

    @GetMapping("/{postId}")
    public Post getPostById(@PathVariable Long postId) {
        return postService.getPostById(postId);
    }
    @GetMapping
    public List<Post> getAllPosts() {
        return postService.findAll();
    }

    @DeleteMapping("/{postId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);
    }

}
