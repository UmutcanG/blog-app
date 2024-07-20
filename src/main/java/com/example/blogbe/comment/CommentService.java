package com.example.blogbe.comment;

import com.example.blogbe.UserInfo.UserInfo;
import com.example.blogbe.UserInfo.UserInfoRepository;
import com.example.blogbe.post.Post;
import com.example.blogbe.post.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserInfoRepository userInfoRepository;


    public Comment save(Comment comment, Long postId, Long authorId) {
        UserInfo author = userInfoRepository.findById(authorId)
                .orElseThrow(() -> new RuntimeException("Author not found"));
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        comment.setUser(author);
        comment.setPost(post);
        return commentRepository.save(comment);
    }

    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }

    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }
}
