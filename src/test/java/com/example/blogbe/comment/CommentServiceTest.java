package com.example.blogbe.comment;

import com.example.blogbe.UserInfo.UserInfo;
import com.example.blogbe.UserInfo.UserInfoRepository;
import com.example.blogbe.post.Post;
import com.example.blogbe.post.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CommentServiceTest {

    private CommentRepository commentRepository;
    private PostRepository postRepository;
    private UserInfoRepository userInfoRepository;
    private CommentService commentService;
    private UserInfo author;
    private Post post;
    private Comment comment;


    @BeforeEach
    void setUp() {
        postRepository = Mockito.mock(PostRepository.class);
        commentRepository = Mockito.mock(CommentRepository.class);
        userInfoRepository = Mockito.mock(UserInfoRepository.class);

        commentService = new CommentService(commentRepository,postRepository,userInfoRepository);

        author = new UserInfo();
        author.setId(1L);
        author.setName("author");

        post = new Post();
        post.setId(1L);
        post.setTitle("post");
        post.setContent("post content");

        comment = new Comment();
        comment.setContent("comment");

    }

    @Test
    void save_valid_commentSaved() {
        when(userInfoRepository.findById(1L)).thenReturn(Optional.of(author));
        when(postRepository.findById(1L)).thenReturn(Optional.of(post));
        when(commentRepository.save(any(Comment.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Comment savedComment = commentService.save(comment, 1L, 1L);

        assertNotNull(savedComment);
        assertEquals("comment", savedComment.getContent());
        assertEquals(author, savedComment.getUser());
        assertEquals(post, savedComment.getPost());

        verify(userInfoRepository, times(1)).findById(1L);
        verify(postRepository, times(1)).findById(1L);
        verify(commentRepository, times(1)).save(comment);
    }

    @Test
    void save_authorNotFound_throwsException() {
        when(userInfoRepository.findById(1L)).thenReturn(Optional.empty());
        when(postRepository.findById(1L)).thenReturn(Optional.of(post));

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            commentService.save(comment, 1L, 1L);
        });

        assertEquals("Author not found", thrown.getMessage());

        verify(userInfoRepository, times(1)).findById(1L);
        verify(postRepository, times(0)).findById(anyLong());
        verify(commentRepository, times(0)).save(any(Comment.class));
    }

    @Test
    void save_PostNotFound_throwsException() {
        when(userInfoRepository.findById(1L)).thenReturn(Optional.of(author));
        when(postRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            commentService.save(comment, 1L, 1L);
        });

        assertEquals("Post not found", thrown.getMessage());

        verify(userInfoRepository, times(1)).findById(1L);
        verify(postRepository, times(1)).findById(1L);
        verify(commentRepository, times(0)).save(any(Comment.class));
    }

    @Test
    void getAllComments_commentsExist_returnComments() {
        Comment comment1 = new Comment();
        comment1.setContent("Comment 1");

        Comment comment2 = new Comment();
        comment2.setContent("Comment 2");

        List<Comment> comments = new ArrayList<>();
        comments.add(comment1);
        comments.add(comment2);

        when(commentRepository.findAll()).thenReturn(comments);

        List<Comment> foundComments = commentService.getAllComments();

        assertNotNull(foundComments);
        assertEquals(2, foundComments.size());
        assertEquals("Comment 1", foundComments.get(0).getContent());
        assertEquals("Comment 2", foundComments.get(1).getContent());

        verify(commentRepository, times(1)).findAll();
    }
}