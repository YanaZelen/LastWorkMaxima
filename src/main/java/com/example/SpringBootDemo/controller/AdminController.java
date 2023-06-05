package com.example.SpringBootDemo.controller;

import com.example.SpringBootDemo.model.Post;
import com.example.SpringBootDemo.model.Role;
import com.example.SpringBootDemo.model.User;
import com.example.SpringBootDemo.service.PostService;
import com.example.SpringBootDemo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Comparator;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class AdminController {
    private final UserService userService;
    private final PostService postService;

    @GetMapping("/index")
    public ModelAndView allUsers() {
        List<User> users = userService.listAll();
        List<Post> posts = postService.listAll();
        posts.sort(Comparator.comparing(Post::getDateCreate).reversed());
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        User user = (User) SecurityContextHolder.getContext() .getAuthentication().getPrincipal();
        User userInRepo = userService.get(user.getId());
        Post post = new Post();
        List<Post> userPosts = postService.postsByUser(userInRepo.getId());
        post.setUser(userInRepo);

        modelAndView.addObject("authUser", userInRepo);
        modelAndView.addObject("users", users);
        modelAndView.addObject("user", new User());
        modelAndView.addObject("roles", Role.values());
        modelAndView.addObject("posts", posts);
        modelAndView.addObject("userPosts", userPosts);
        modelAndView.addObject("post", post);
        return modelAndView;
    }

    @GetMapping(value = "/admin/post")
    public ResponseEntity<List<Post>> getAllPost() {
        List<Post> posts = postService.listAll();
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        boolean isAdmin = user.getAuthorities().stream().anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN"));
        if (!isAdmin)
            posts = null;
        else
            posts.sort(Comparator.comparing(Post::getDateCreate).reversed());
        return posts != null && !posts.isEmpty() ?
                new ResponseEntity<>(posts, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/admin/post/{id}")
    public ResponseEntity<Post> getPost(@PathVariable(name = "id") Long id) {
        Post post = postService.get(id);
        return post != null ? new ResponseEntity<>(post, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping(value = "/admin/post/{id}")
    public ResponseEntity<Post> deletePost(@PathVariable(name = "id") Long id) {
        postService.get(id);
        postService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
