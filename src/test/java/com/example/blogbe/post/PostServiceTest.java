package com.example.blogbe.post;

import com.example.blogbe.UserInfo.UserInfo;
import com.example.blogbe.UserInfo.UserInfoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PostServiceTest {

    private PostService postService;
    private PostRepository postRepository;
    private UserInfoRepository userInfoRepository;

    @BeforeEach
    void setUp() {
        postRepository = Mockito.mock(PostRepository.class);
        userInfoRepository = Mockito.mock(UserInfoRepository.class);
        postService = new PostService(postRepository,userInfoRepository);

    }
    @InjectMocks
    private UserInfo author;
    private Post post;

    @Test
    void testCreatePost() {
        author = new UserInfo();
        author.setId(1L);
        author.setName("author");

        post = new Post();
        post.setTitle("title");
        post.setContent("content");

        when(userInfoRepository.findById(1L)).thenReturn(Optional.of(author));
        when(postRepository.save(any(Post.class))).thenReturn(post);

        Post createdPost = postService.createPost(post, 1L);

        assertNotNull(createdPost);
        assertEquals("title", createdPost.getTitle());
        assertEquals("content", createdPost.getContent());
        assertEquals(author, createdPost.getAuthor());

        verify(userInfoRepository, times(1)).findById(1L);
        verify(postRepository, times(1)).save(post);
    }

    @Test
    void testCreatePost_authorNotfound_throwsException() {
        author = new UserInfo();
        author.setId(1L);
        author.setName("author");

        post = new Post();
        post.setTitle("title");
        post.setContent("content");

        when(userInfoRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            postService.createPost(post, 1L);
        });

        assertEquals("Author not found", exception.getMessage());

        verify(userInfoRepository, times(1)).findById(1L);
        verify(postRepository, times(0)).save(any(Post.class));
    }

    @Test
    void testFindAll() {
        author = new UserInfo();
        author.setId(1L);
        author.setName("author");

        post = new Post();
        post.setTitle("title");
        post.setContent("content");

        List<Post> posts = new ArrayList<>();
        posts.add(post);

        when(postRepository.findAll()).thenReturn(posts);
        List<Post> foundPosts = postService.findAll();

        assertNotNull(foundPosts);
        assertEquals(1, foundPosts.size());
        assertEquals("title", foundPosts.get(0).getTitle());

        verify(postRepository, times(1)).findAll();


    }
}