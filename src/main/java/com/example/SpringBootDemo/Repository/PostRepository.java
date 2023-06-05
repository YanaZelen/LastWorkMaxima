package com.example.SpringBootDemo.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.example.SpringBootDemo.model.Post;
import com.example.SpringBootDemo.model.User;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

  List<Post> findAll();

  void deleteById(Long id);

  List<Post> findByUserId(Long userId);
}
