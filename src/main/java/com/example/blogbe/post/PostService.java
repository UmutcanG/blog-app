package com.example.blogbe.post;


import com.example.blogbe.UserInfo.UserInfo;
import com.example.blogbe.UserInfo.UserInfoRepository;
import com.example.blogbe.UserInfo.UserInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PostService {


    private final PostRepository postRepository;
    private final UserInfoRepository userInfoRepository;

    public Post createPost(Post post) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        UserInfo author = userInfoRepository.findByUsername(username);
        if (author == null) {
            throw new RuntimeException("Author not found");
        }

        if (!author.getRoles().contains("ADMIN")) {
            throw new RuntimeException("User does not have the necessary role to create a post");
        }

        post.setAuthor(author);
        return postRepository.save(post);
    }

    public Post getPostById(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));
    }

    public List<Post> findAll() {
        return postRepository.findAll();
    }

    public void deletePost(Long postId) {
        postRepository.deleteById(postId);
    }
}
