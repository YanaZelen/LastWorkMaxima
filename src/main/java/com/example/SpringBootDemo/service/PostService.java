package com.example.SpringBootDemo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.SpringBootDemo.model.Post;
import com.example.SpringBootDemo.Repository.PostRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {

  private final PostRepository postRepository;

  public List<Post> postsByUser(Long userId) {
    return postRepository.findByUserId(userId);
  }

  public Post save(Post post) {
    return postRepository.save(post);
  }

  public List<Post> listAll() {
    return postRepository.findAll();
  }

  public Post get(Long id) {
    return postRepository.findById(id).get();
  }

  public void delete(Long id) {
    postRepository.deleteById(id);
  }
}
