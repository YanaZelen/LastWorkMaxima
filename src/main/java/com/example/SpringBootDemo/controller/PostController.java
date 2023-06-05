package com.example.SpringBootDemo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import com.example.SpringBootDemo.model.Post;
import com.example.SpringBootDemo.model.User;
import com.example.SpringBootDemo.service.PostService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/user/post")
public class PostController {

    private final PostService postService;

    @PostMapping()
    public ResponseEntity<Post> createPost(@RequestBody Post post) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        post.setUser(user);
        post.setDateCreate(LocalDate.now());
        postService.save(post);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Post> getPost(@PathVariable(name = "id") Long id) {
        Post post = postService.get(id);
        return post != null ? new ResponseEntity<>(post, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping(value = "/")
    public ResponseEntity<Post> updatePost(@RequestBody Post post) {
        post.setDateCreate(LocalDate.now());
        post.setUser(post.getUser());
        postService.save(post);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Post> deletePost(@PathVariable(name = "id") Long id) {
        postService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/postline/")
    public ResponseEntity<List<Post>> getAllMyPost() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Post> posts = postService.postsByUser(user.getId());
        return posts != null && !posts.isEmpty() ? new ResponseEntity<>(posts, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping(value = "/postline/")
    public ResponseEntity<Post> createMyPost(@RequestBody Post post) {
        post.setDateCreate(LocalDate.now());
        postService.save(post);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping(value = "/postline")
    public ResponseEntity<Post> updateMyPost(@RequestBody Post post) {
        Post editPost = postService.get(post.getId());
        editPost.setDateCreate(LocalDate.now());
        editPost.setTitle(post.getTitle());
        editPost.setText(post.getText());
        postService.save(editPost);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/postline/{id}")
    public ResponseEntity<Post> getMyPost(@PathVariable(name = "id") Long id) {
        Post post = postService.get(id);
        return post != null ? new ResponseEntity<>(post, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping(value = "/postline/{id}")
    public ResponseEntity<Post> deleteMyPost(@PathVariable(name = "id") Long id) {
        postService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}