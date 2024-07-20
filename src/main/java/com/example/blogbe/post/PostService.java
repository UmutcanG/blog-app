package com.example.blogbe.post;


import com.example.blogbe.UserInfo.UserInfo;
import com.example.blogbe.UserInfo.UserInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PostService {


    private final PostRepository postRepository;
    private final UserInfoRepository userInfoRepository;

    public Post createPost(Post post, Long authorId) {
        UserInfo author = userInfoRepository.findById(authorId)
                .orElseThrow(() -> new RuntimeException("Author not found"));

        post.setAuthor(author);
        return postRepository.save(post);
    }



    public List<Post> findAll() {
        return postRepository.findAll();
    }
}
