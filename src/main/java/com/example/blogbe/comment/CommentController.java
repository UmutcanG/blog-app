package com.example.blogbe.comment;

import com.example.blogbe.UserInfo.UserInfoRepository;
import com.example.blogbe.post.Post;
import com.example.blogbe.post.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;


    @PostMapping("/add-comment")
    public Comment addComment(@RequestBody Comment comment,
                              @RequestParam Long postId,
                              @RequestParam Long authorId) {
        return commentService.save(comment,postId,authorId);
    }


    @GetMapping
    public List<Comment> getAllComments() {
        return commentService.getAllComments();
    }

    @DeleteMapping("/delete-comment/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
    }

}
